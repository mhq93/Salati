package com.mhq.salati.qibla.presentation.screens

import com.mhq.salati.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.shared.presentation.components.BottomNavDefaults
import com.mhq.salati.shared.presentation.components.TabHeader
import com.mhq.salati.shared.presentation.components.asString
import com.mhq.salati.shared.presentation.screens.LoadingContent
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.SheetBackground

@Composable
fun QiblaContent(
    state: QiblaContract.State,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SheetBackground)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = BottomNavDefaults.Height)
        ) {
            when {
                state.isLoading -> {
                    LoadingContent(modifier = Modifier.weight(1f))
                }

                state.errorMessage != null -> {
                    QiblaContentError(
                        errorMessage = state.errorMessage.asString(),
                        sensorUnavailable = state.sensorUnavailable,
                        isLocationPermissionPermanentlyDenied = state.isLocationPermissionPermanentlyDenied,
                        areLocationServicesDisabled = state.areLocationServicesDisabled,
                        isLocationPermissionRequired = state.isLocationPermissionRequired, // NEW
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }

                state.qiblaBearing != null -> {
                    TabHeader(
                        icon = Icons.Default.Explore,
                        title = stringResource(R.string.qibla_direction),
                        subtitle = stringResource(R.string.face_the_kaaba_wherever_you_are)
                    )
                    QiblaContentSuccess(
                        locationName = state.locationName,
                        deviceHeading = state.deviceHeading,
                        qiblaBearing = state.qiblaBearing,
                        compassAccuracy = state.compassAccuracy,
                        onLocationPillClick = { onIntent(QiblaContract.Intent.LocationPillClicked) },
                        onRecalibrateClick = { onIntent(QiblaContract.Intent.RecalibrateClicked) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun QiblaContentPreview() {
    SalatiTheme() {
        QiblaContent(
            state = QiblaContract.State(),
            onIntent = {}
        )
    }
}