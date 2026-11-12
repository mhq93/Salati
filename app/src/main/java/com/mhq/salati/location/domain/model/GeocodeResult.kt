package com.mhq.salati.location.domain.model

/**
 * The outcome of a geocoding call, forward or reverse.
 *
 * [Found] may carry one or both names as null;
 * only a call where both are null counts as [NotFound] at the use-case level.
 */
sealed interface GeocodeResult {
    /** The place was resolved; either name may still be missing. */
    data class Found(val cityName: String?, val countryName: String?) : GeocodeResult

    /** The geo-coder answered but recognized no place. */
    data object NotFound : GeocodeResult

    /** The geo-coder itself errored; [throwable] carries the cause. */
    data class Failed(val throwable: Throwable) : GeocodeResult
}