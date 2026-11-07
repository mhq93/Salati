package com.mhq.salati.onboarding.domain.usecases

import com.mhq.salati.onboarding.domain.model.OnboardingPermissions
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import javax.inject.Inject

class CheckOnboardingPermissionsUseCase @Inject constructor(
    private val permissionChecker: PermissionChecker
) {
    operator fun invoke(): OnboardingPermissions = OnboardingPermissions(
        hasNotification = permissionChecker.hasNotificationPermission(),
        hasExactAlarm = permissionChecker.canScheduleExactAlarms()
    )
}