package com.mhq.salati.onboarding.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.ui.graphics.vector.ImageVector
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.PrimaryActionUi
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.StepUi

/** The step's badge icon, or null when the step shows the app logo instead. */
val StepUi.icon: ImageVector?
    get() = when (this) {
        StepUi.WELCOME -> null
        StepUi.CALCULATION -> Icons.AutoMirrored.Filled.MenuBook
        StepUi.ALERTS -> Icons.Default.NotificationsActive
    }

val StepUi.titleRes: Int
    @StringRes get() = when (this) {
        StepUi.WELCOME -> R.string.app_name
        StepUi.CALCULATION -> R.string.onboarding_times_title
        StepUi.ALERTS -> R.string.notifications
    }

val StepUi.subtitleRes: Int
    @StringRes get() = when (this) {
        StepUi.WELCOME -> R.string.app_motto
        StepUi.CALCULATION -> R.string.onboarding_calculation_subtitle
        StepUi.ALERTS -> R.string.onboarding_notifications_subtitle
    }

val PrimaryActionUi.labelRes: Int
    @StringRes get() = when (this) {
        PrimaryActionUi.CONTINUE -> R.string.onboarding_continue
        PrimaryActionUi.GET_STARTED -> R.string.get_started
        PrimaryActionUi.ALLOW_NOTIFICATIONS -> R.string.onboarding_notifications_action
        PrimaryActionUi.ALLOW_EXACT_ALARMS -> R.string.onboarding_exact_alarm_action
        PrimaryActionUi.OPEN_APP_SETTINGS -> R.string.action_open_settings
    }