package com.mhq.salati.alarms.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.alarms.presentation.viewmodel.AlarmsViewModel
import com.mhq.salati.shared.presentation.components.LocalSnackbarHostState
import com.mhq.salati.shared.presentation.components.asString
import kotlinx.coroutines.launch

@Composable
fun CustomAlarmsContainer(
    viewModel: AlarmsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AlarmsContract.Effect.ShowError ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            effect.message.asString(context)
                        )
                    }

                is AlarmsContract.Effect.AlarmSaved -> Unit
            }
        }
    }

    CustomAlarmsContent(
        state = state,
        onIntent = viewModel::onIntent
    )
}