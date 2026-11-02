package com.mhq.salati.onboarding.domain.model

/** What the user has already granted, as far as onboarding cares. */
data class OnboardingPermissions(
    val hasLocation: Boolean,
    val hasNotification: Boolean,
    val hasExactAlarm: Boolean
)