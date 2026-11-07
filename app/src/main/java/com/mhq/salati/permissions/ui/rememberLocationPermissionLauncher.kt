package com.mhq.salati.permissions.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberLocationPermissionLauncher(
    onGranted: () -> Unit,
    onDenied: (showRationale: Boolean) -> Unit
): ActivityResultLauncher<String> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onGranted() else onDenied(context.shouldShowLocationRationale())
    }
}