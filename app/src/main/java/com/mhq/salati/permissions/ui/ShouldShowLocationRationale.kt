package com.mhq.salati.permissions.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.core.app.ActivityCompat

/**
 * True while the system would still show its location dialog after a refusal. Only the UI layer can
 * ask the system, so screens report this to their ViewModel.
 */
fun Context.shouldShowLocationRationale(): Boolean {
    var current: Context = this
    while (current is ContextWrapper) {
        if (current is Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale(
                current,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        current = current.baseContext
    }
    return false
}