package com.mhq.salati.onboarding.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule

val onboardingPages = listOf(
    OnboardingPageData(
        icon = Icons.Default.Schedule,
        title = "Never Miss a Prayer",
        description = "Accurate prayer times calculated for your exact location, updated automatically throughout the day."
    ),
    OnboardingPageData(
        icon = Icons.Default.Explore,
        title = "Find Your Qibla",
        description = "A precise, live compass points you toward the Kaaba wherever you are."
    ),
    OnboardingPageData(
        icon = Icons.Default.NotificationsActive,
        title = "Gentle Reminders",
        description = "Custom Adhan alerts for every prayer, fully in your control."
    )
)