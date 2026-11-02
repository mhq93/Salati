package com.mhq.salati.permissions.ui

import android.Manifest
import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

@Composable
fun rememberNotificationPermissionLauncher(
    onGranted: () -> Unit,
    onDenied: (permanentlyDenied: Boolean) -> Unit
): ManagedActivityResultLauncher<String, Boolean> {
    val activity = LocalContext.current as? Activity

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGranted()
        } else {
            val canAskAgain = activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } ?: true
            onDenied(!canAskAgain)
        }
    }
}