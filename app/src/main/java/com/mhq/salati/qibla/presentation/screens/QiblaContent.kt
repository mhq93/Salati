package com.mhq.salati.qibla.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.qibla.presentation.components.QiblaHeader
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.shared.presentation.components.BottomNavDefaults
import com.mhq.salati.shared.presentation.components.asString
import com.mhq.salati.shared.presentation.screens.AwaitingLocationPermissions
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
            QiblaHeader()

            when {
                state.isLoading -> {
                    LoadingContent(modifier = Modifier.weight(1f))
                }

                state.errorMessage != null -> {
                    QiblaContentError(
                        errorMessage = state.errorMessage.asString(),
                        sensorUnavailable = state.sensorUnavailable,
                        locationPermission = state.locationPermission,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }

                state.qiblaBearing != null -> {
                    QiblaContentSuccess(
                        locationName = state.locationName,
                        deviceHeading = state.deviceHeading,
                        qiblaBearing = state.qiblaBearing,
                        compassAccuracy = state.compassAccuracy,
                        onLocationPillClick = { onIntent(QiblaContract.Intent.LocationPillClicked) },
                        onRecalibrateClick = { onIntent(QiblaContract.Intent.RecalibrateClicked) }
                    )
                }

                else -> {
                    AwaitingLocationPermissions(modifier = Modifier.weight(1f))
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