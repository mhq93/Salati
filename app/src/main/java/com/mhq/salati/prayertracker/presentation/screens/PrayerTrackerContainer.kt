package com.mhq.salati.prayertracker.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.prayertracker.presentation.components.PrayerConfirmDialog
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract
import com.mhq.salati.prayertracker.presentation.viewmodel.PrayerTrackerViewModel
import com.mhq.salati.shared.presentation.components.LocalSnackbarHostState
import com.mhq.salati.shared.presentation.components.asString
import kotlinx.coroutines.launch

@Composable
fun PrayerTrackerContainer(
    prayerTrackerViewModel: PrayerTrackerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by prayerTrackerViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    // NEW: Collect effects
    LaunchedEffect(Unit) {
        prayerTrackerViewModel.effect.collect { effect ->
            when (effect) {
                is PrayerTrackerContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    PrayerTrackerContent(
        state = state,
        onIntent = prayerTrackerViewModel::onIntent
    )

    state.dialogPrayer?.let { prayer ->
        PrayerConfirmDialog(
            prayer = prayer,
            onConfirmPrayed = { prayerTrackerViewModel.onIntent(PrayerTrackerContract.Intent.ConfirmPrayed) },
            onConfirmMissed = { prayerTrackerViewModel.onIntent(PrayerTrackerContract.Intent.ConfirmMissed) },
            onDismiss = { prayerTrackerViewModel.onIntent(PrayerTrackerContract.Intent.DismissDialog) }
        )
    }
}