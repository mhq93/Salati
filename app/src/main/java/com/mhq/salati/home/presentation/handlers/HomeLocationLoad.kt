package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.model.SavedLocation

/** The steps of working out where the user is, in the order they happen. */
sealed interface HomeLocationLoad {

    /** A place the prayer times can be loaded for. */
    sealed interface Located : HomeLocationLoad {
        val location: SavedLocation
    }

    /** The place stored from an earlier run or picked by hand. */
    data class Saved(override val location: SavedLocation) : Located

    /** The place just found through GPS. */
    data class Found(override val location: SavedLocation) : Located

    /** There is no saved place, so a GPS lookup has started. */
    data object LookingUpCurrentLocation : HomeLocationLoad

    /** Something the lookup needs is missing; [prerequisites] says what. */
    data class Blocked(val prerequisites: LocationPrerequisites) : HomeLocationLoad

    data object GeocodingFailed : HomeLocationLoad
    data object Unavailable : HomeLocationLoad
}