package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.location.domain.repo.LocationRepository
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.testutil.FakeConnectivityChecker
import com.mhq.salati.testutil.FakePermissionChecker
import com.mhq.salati.testutil.FakeSettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class ResolveLocationUseCaseTest {

    private class FakeLocationProvider(
        private val enabled: Boolean,
        private val lookupResult: CoordinatesLookup
    ) : LocationProvider {
        override fun isLocationEnabled() = enabled

        // Drives GetCurrentCoordinatesUseCase once its own checks pass:
        // a slow fix becomes TimedOut, throws become Failed / PermissionDenied.
        override suspend fun getCurrentLocation(): Coordinates = when (lookupResult) {
            is CoordinatesLookup.Found -> lookupResult.coordinates
            is CoordinatesLookup.TimedOut -> {
                delay(10_000.milliseconds) // GPS timeout is 5s; runTest's virtual clock makes this instantaneous.
                throw IllegalStateException("unreachable")
            }
            is CoordinatesLookup.Failed -> throw IllegalStateException("gps broke")
            is CoordinatesLookup.PermissionDenied -> throw SecurityException("denied")
            is CoordinatesLookup.ServicesDisabled,
            is CoordinatesLookup.Locating -> throw IllegalStateException("unreachable")
        }
    }

    private class FakeGeocoder(private val result: GeocodeResult) : GeocoderProvider {
        override suspend fun reverseGeocode(coordinates: Coordinates, acceptLanguage: String?): GeocodeResult = result

        override suspend fun searchByName(query: String, acceptLanguage: String?): List<LocationSearchResult> = emptyList()
    }

    private class FakeLocationRepository : LocationRepository {
        val saved = mutableListOf<SavedLocation>()
        override val savedLocation: Flow<SavedLocation?> = flowOf(null)
        override suspend fun saveManualLocation(location: SavedLocation) {
            saved.add(location)
        }
        override suspend fun clearSavedLocation() = Unit
    }

    private val coordinates = Coordinates(30.0444, 31.2357)

    private fun useCase(
        hasInternet: Boolean = true,
        servicesEnabled: Boolean = true,
        permissionGranted: Boolean = true,
        gpsServicesEnabled: Boolean = servicesEnabled,
        lookupResult: CoordinatesLookup = CoordinatesLookup.Found(coordinates),
        geocodeResult: GeocodeResult = GeocodeResult.Found("Cairo", "Egypt")
    ): Pair<ResolveLocationUseCase, FakeLocationRepository> {
        val locationRepo = FakeLocationRepository()
        val useCase = ResolveLocationUseCase(
            checkLocationPrerequisitesUseCase = CheckLocationPrerequisitesUseCase(
                connectivityChecker = FakeConnectivityChecker(hasInternet),
                locationProvider = FakeLocationProvider(servicesEnabled, lookupResult),
                permissionChecker = FakePermissionChecker(permissionGranted)
            ),
            getCurrentCoordinatesUseCase = GetCurrentCoordinatesUseCase(
                locationProvider = FakeLocationProvider(gpsServicesEnabled, lookupResult),
                permissionChecker = FakePermissionChecker(permissionGranted)
            ),
            reverseGeocodeLocationUseCase = ReverseGeocodeLocationUseCase(FakeGeocoder(geocodeResult)),
            saveManualLocationUseCase = SaveManualLocationUseCase(locationRepo),
            observeSettingsUseCase = ObserveSettingsUseCase(FakeSettingsRepository())
        )
        return useCase to locationRepo
    }

    // --- Blocked cases ---

    @Test
    fun `no internet is Blocked before any GPS work happens`() = runTest {
        val (useCase, repo) = useCase(hasInternet = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(false, blocked.prerequisites.hasInternet)
        assertEquals(true, blocked.prerequisites.servicesEnabled)
        assertEquals(true, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `disabled location services is Blocked`() = runTest {
        val (useCase, repo) = useCase(servicesEnabled = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(true, blocked.prerequisites.hasInternet)
        assertEquals(false, blocked.prerequisites.servicesEnabled)
        assertEquals(true, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `missing permission is Blocked`() = runTest {
        val (useCase, repo) = useCase(permissionGranted = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(true, blocked.prerequisites.hasInternet)
        assertEquals(true, blocked.prerequisites.servicesEnabled)
        assertEquals(false, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `permission lost between the check and the GPS fix is Blocked`() = runTest {
        val (useCase, repo) = useCase(lookupResult = CoordinatesLookup.PermissionDenied)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(false, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `services lost between the check and the GPS fix is Blocked`() = runTest {
        val (useCase, repo) = useCase(gpsServicesEnabled = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(false, blocked.prerequisites.servicesEnabled)
        assertTrue(repo.saved.isEmpty())
    }

    // --- Success paths ---

    @Test
    fun `a GPS fix with a successful geocode resolves and saves the place`() = runTest {
        val (useCase, repo) = useCase()

        val result = useCase()

        val resolved = result as LocationResolution.Resolved
        assertEquals(coordinates, resolved.location.coordinates)
        assertEquals("Cairo", resolved.location.cityName)
        assertEquals("Egypt", resolved.location.countryName)
        assertEquals(listOf(resolved.location), repo.saved)
    }

    @Test
    fun `a geocode NotFound still resolves with unnamed coordinates`() = runTest {
        val (useCase, repo) = useCase(geocodeResult = GeocodeResult.NotFound)

        val result = useCase()

        val resolved = result as LocationResolution.Resolved
        assertEquals(coordinates, resolved.location.coordinates)
        assertNull(resolved.location.cityName)
        assertNull(resolved.location.countryName)
        assertEquals(listOf(resolved.location), repo.saved)
    }

    // --- Other failure paths ---

    @Test
    fun `a GPS timeout is LocationUnavailable`() = runTest {
        val (useCase, repo) = useCase(lookupResult = CoordinatesLookup.TimedOut)

        val result = useCase()

        assertEquals(LocationResolution.LocationUnavailable, result)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `a GPS failure is LocationUnavailable`() = runTest {
        val (useCase, repo) = useCase(lookupResult = CoordinatesLookup.Failed("gps broke"))

        val result = useCase()

        assertEquals(LocationResolution.LocationUnavailable, result)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `a geocode failure is GeocodingFailed`() = runTest {
        val (useCase, repo) = useCase(geocodeResult = GeocodeResult.Failed(IllegalStateException("nominatim down")))

        val result = useCase()

        assertEquals(LocationResolution.GeocodingFailed, result)
        assertTrue(repo.saved.isEmpty())
    }
}