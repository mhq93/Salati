package com.mhq.salati.shared

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mhq.salati.shared.navigation.Screen
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.splash.presentation.contract.SplashContract
import com.mhq.salati.splash.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.state.value.isLoading
        }

        setContent {
            var startDestination by remember { mutableStateOf<Screen?>(null) }

            LaunchedEffect(Unit) {
                splashViewModel.effect.collect { effect ->
                    when (effect) {
                        is SplashContract.Effect.NavigateTo -> {
                            startDestination = effect.screen
                        }
                    }
                }
            }

            SalatiTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    startDestination?.let {
                        SalatiApp(startDestination = it)
                    }
                }
            }
        }
    }
}