package com.mhq.salati.presentation.qibla.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mhq.salati.presentation.common.screens.AwaitingLocationPermissions
import com.mhq.salati.presentation.common.screens.LoadingContent
import com.mhq.salati.presentation.qibla.QiblaContract
import com.mhq.salati.presentation.qibla.components.LocationPill
import com.mhq.salati.presentation.qibla.components.QiblaHeader
import com.mhq.salati.presentation.theme.CardBackground
import com.mhq.salati.presentation.theme.SalatiTheme
import com.mhq.salati.presentation.theme.SheetBackground

@Composable
fun QiblaContent(
    state: QiblaContract.State,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    var headerHeightPx by remember { mutableIntStateOf(0) }
    var pillHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SheetBackground)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.onGloballyPositioned { headerHeightPx = it.size.height }
            ) {
                QiblaHeader()
            }

            when {
                state.isLoading -> {
                    LoadingContent(modifier = Modifier.weight(1f))
                }

                state.errorMessage != null -> {
                    QiblaErrorContent(
                        errorMessage = state.errorMessage,
                        sensorUnavailable = state.sensorUnavailable,
                        locationPermission = state.locationPermission,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }

                state.qiblaBearing != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Spacer(
                            Modifier.height(pillHeightPx.let {
                                with(LocalDensity.current) { (it / 2).toDp() }
                            } + 12.dp))

                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = CardBackground),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp)
                            ) {
                                QiblaSuccessContent(
                                    deviceHeading = state.deviceHeading,
                                    qiblaBearing = state.qiblaBearing,
                                    compassAccuracy = state.compassAccuracy,
                                    onRecalibrateClick = { onIntent(QiblaContract.Intent.RecalibrateClicked) }
                                )
                            }
                        }
                    }
                }

                else -> {
                    AwaitingLocationPermissions(modifier = Modifier.weight(1f))
                }
            }
        }

        if (state.qiblaBearing != null) {
            LocationPill(
                locationName = "Located in Alexandria, Egypt",
                onClick = { onIntent(QiblaContract.Intent.LocationPillClicked) },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .onGloballyPositioned { pillHeightPx = it.size.height }
                    .offset {
                        IntOffset(
                            x = 0,
                            y = headerHeightPx - pillHeightPx / 2
                        )
                    }
            )
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