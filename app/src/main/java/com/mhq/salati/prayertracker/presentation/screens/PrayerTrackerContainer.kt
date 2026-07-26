package com.mhq.salati.prayertracker.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mhq.salati.prayertracker.presentation.components.PrayerConfirmDialog
import com.mhq.salati.prayertracker.presentation.viewmodel.PrayerTrackerViewModel

@Composable
fun PrayerTrackerContainer(
    viewModel: PrayerTrackerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    PrayerTrackerContent(
        state = state,
        onIntent = viewModel::onIntent
    )

    state.dialogPrayer?.let { prayer ->
        PrayerConfirmDialog(
            prayer = prayer,
            onConfirmPrayed = { viewModel.onIntent(com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent.ConfirmPrayed) },
            onConfirmMissed = { viewModel.onIntent(com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent.ConfirmMissed) },
            onDismiss = { viewModel.onIntent(com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent.DismissDialog) }
        )
    }
    // TODO: collect viewModel.effect for ShowError, same as Home/Qibla/Settings
}