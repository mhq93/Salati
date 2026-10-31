package com.mhq.salati.splash.presentation.mapper

import com.mhq.salati.splash.domain.model.StartDestination
import com.mhq.salati.splash.presentation.model.StartDestinationUi

fun StartDestination.toUi(): StartDestinationUi = when (this) {
    StartDestination.ONBOARDING -> StartDestinationUi.ONBOARDING
    StartDestination.HOME -> StartDestinationUi.HOME
}