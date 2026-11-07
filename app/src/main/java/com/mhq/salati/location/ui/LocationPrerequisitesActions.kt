package com.mhq.salati.location.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.mhq.salati.permissions.ui.rememberLocationPermissionLauncher

/** What the blocked screen's buttons do on the device; Home and Qibla share it. */
class LocationPrerequisitesActions internal constructor(
    private val context: Context,
    private val permissionLauncher: ActivityResultLauncher<String>
) {
    fun requestPermission() {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivitySafely(intent)
    }

    fun openLocationSettings() {
        context.startActivitySafely(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    fun openInternetSettings() {
        val panel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
        } else {
            Intent(Settings.ACTION_WIRELESS_SETTINGS)
        }
        context.startActivitySafely(panel, fallback = Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}

@Composable
fun rememberLocationPrerequisitesActions(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (showRationale: Boolean) -> Unit
): LocationPrerequisitesActions {
    val context = LocalContext.current
    val permissionLauncher = rememberLocationPermissionLauncher(
        onGranted = onPermissionGranted,
        onDenied = onPermissionDenied
    )
    return remember(context, permissionLauncher) {
        LocationPrerequisitesActions(context, permissionLauncher)
    }
}

private fun Context.startActivitySafely(intent: Intent, fallback: Intent? = null) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        fallback?.let { runCatching { startActivity(it) } }
    }
}