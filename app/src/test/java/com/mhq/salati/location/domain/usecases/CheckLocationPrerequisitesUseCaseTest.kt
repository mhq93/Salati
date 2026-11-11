package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.testutil.FakeConnectivityChecker
import com.mhq.salati.testutil.FakePermissionChecker
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CheckLocationPrerequisitesUseCaseTest {

    private class FakeLocationProvider(private val enabled: Boolean) : LocationProvider {
        override fun isLocationEnabled() = enabled
        override suspend fun getCurrentLocation() = throw UnsupportedOperationException()
    }

    /** Builds the use case over fakes and runs it, returning the resulting [LocationPrerequisites]. */
    private suspend fun check(
        hasInternet: Boolean = true,
        servicesEnabled: Boolean = true,
        permissionGranted: Boolean = true
    ): LocationPrerequisites {
        val useCase = CheckLocationPrerequisitesUseCase(
            connectivityChecker = FakeConnectivityChecker(hasInternet),
            locationProvider = FakeLocationProvider(servicesEnabled),
            permissionChecker = FakePermissionChecker(permissionGranted)
        )
        return useCase()
    }

    @Test
    fun `all conditions met returns met prerequisites`() = runTest {
        val result = check()
        assertEquals(true, result.hasInternet)
        assertEquals(true, result.servicesEnabled)
        assertEquals(true, result.permissionGranted)
        assertEquals(true, result.areMet)
    }

    @Test
    fun `missing internet is reported on its own`() = runTest {
        val result = check(hasInternet = false)
        assertEquals(false, result.hasInternet)
        assertEquals(true, result.servicesEnabled)
        assertEquals(true, result.permissionGranted)
        assertEquals(false, result.areMet)
    }

    @Test
    fun `disabled location services are reported on their own`() = runTest {
        val result = check(servicesEnabled = false)
        assertEquals(true, result.hasInternet)
        assertEquals(false, result.servicesEnabled)
        assertEquals(true, result.permissionGranted)
        assertEquals(false, result.areMet)
    }

    @Test
    fun `missing permission is reported on its own`() = runTest {
        val result = check(permissionGranted = false)
        assertEquals(true, result.hasInternet)
        assertEquals(true, result.servicesEnabled)
        assertEquals(false, result.permissionGranted)
        assertEquals(false, result.areMet)
    }

    @Test
    fun `all failures are reported together, not just the first one`() = runTest {
        val result = check(hasInternet = false, servicesEnabled = false, permissionGranted = false)
        assertEquals(false, result.hasInternet)
        assertEquals(false, result.servicesEnabled)
        assertEquals(false, result.permissionGranted)
        assertEquals(false, result.areMet)
    }
}