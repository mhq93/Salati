package com.mhq.salati.location.presentation.mapper

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.presentation.model.PrerequisitesUi

fun LocationPrerequisites.toPrerequisitesUi(permissionPermanentlyDenied: Boolean) = PrerequisitesUi(
    hasInternet = hasInternet,
    servicesEnabled = servicesEnabled,
    permissionGranted = permissionGranted,
    permissionPermanentlyDenied = permissionPermanentlyDenied && !permissionGranted
)