package com.mhq.salati.alarms.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.alarms.presentation.viewmodel.AlarmsViewModel
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.asString
import kotlinx.coroutines.launch

@Composable
fun CustomAlarmsContainer(
    alarmsViewModel: AlarmsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by alarmsViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        alarmsViewModel.effect.collect { effect ->
            when (effect) {
                is AlarmsContract.Effect.AlarmSaved -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
                is AlarmsContract.Effect.ShowNotificationsOffHint -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            context.getString(R.string.notifications_off_hint)
                        )
                    }
                }
                is AlarmsContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    CustomAlarmsContent(
        state = state,
        onIntent = alarmsViewModel::onIntent
    )
}