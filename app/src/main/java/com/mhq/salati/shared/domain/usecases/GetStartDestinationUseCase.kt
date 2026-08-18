package com.mhq.salati.shared.domain.usecases

import com.mhq.salati.onboarding.domain.usecase.GetOnboardingCompletedUseCase
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.shared.navigation.Screen
import javax.inject.Inject

class GetStartDestinationUseCase @Inject constructor(
    private val getOnboardingCompletedUseCase: GetOnboardingCompletedUseCase,
    //private val permissionChecker: PermissionChecker
) {
    suspend operator fun invoke(): Screen {
        if (!getOnboardingCompletedUseCase()) return Screen.Onboarding
        //if (!permissionChecker.hasLocationPermission()) return Screen.AwaitingLocationPermissions
        return Screen.Home
    }
}