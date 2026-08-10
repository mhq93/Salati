package com.mhq.salati.splash.domain.usecases

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.shared.navigation.Screen
import javax.inject.Inject

class GetStartDestinationUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val permissionChecker: PermissionChecker
) {
    suspend operator fun invoke(): Screen {
        if (!onboardingRepository.hasCompletedOnboarding()) return Screen.Onboarding
        if (!permissionChecker.hasLocationPermission()) return Screen.AwaitingLocationPermissions
        return Screen.Home
    }
}