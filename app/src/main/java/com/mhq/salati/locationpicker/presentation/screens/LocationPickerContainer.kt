package com.mhq.salati.locationpicker.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract
import com.mhq.salati.locationpicker.presentation.viewmodel.LocationPickerViewModel
import com.mhq.salati.shared.presentation.components.LocalSnackbarHostState
import com.mhq.salati.shared.presentation.components.asString
import kotlinx.coroutines.launch

@Composable
fun LocationPickerContainer(
    onLocationSaved: () -> Unit,
    onBackClicked: () -> Unit,
    locationPickerViewModel: LocationPickerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by locationPickerViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        locationPickerViewModel.effect.collect { effect ->
            when (effect) {
                is LocationPickerContract.Effect.ShowError -> {
                    scope.launch { snackbarHostState.showSnackbar(effect.message.asString(context)) }
                }
                is LocationPickerContract.Effect.LocationSaved -> onLocationSaved()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LocationPickerContent(
            state = state,
            onIntent = locationPickerViewModel::onIntent,
            onBackClicked = onBackClicked
        )
    }
}