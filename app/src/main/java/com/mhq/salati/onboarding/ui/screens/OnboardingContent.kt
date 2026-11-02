package com.mhq.salati.onboarding.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Intent
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.StepUi
import com.mhq.salati.onboarding.ui.components.OnboardingPageIndicator
import com.mhq.salati.onboarding.ui.components.OnboardingSelectionSheet
import com.mhq.salati.onboarding.ui.components.OnboardingStepHeader
import com.mhq.salati.onboarding.ui.icon
import com.mhq.salati.onboarding.ui.labelRes
import com.mhq.salati.onboarding.ui.subtitleRes
import com.mhq.salati.onboarding.ui.titleRes
import com.mhq.salati.settings.ui.components.SettingsCard
import com.mhq.salati.settings.ui.components.SettingsDivider
import com.mhq.salati.settings.ui.components.SettingsSelectorRow
import com.mhq.salati.settings.ui.components.SettingsSwitchRow
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.prayerGradient

@Composable
fun OnboardingContent(
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit
) {
    val isFirstStep = state.step == StepUi.WELCOME
    var activeSelector by remember { mutableStateOf<OnboardingContract.SelectorUi?>(null) }
    var isStepSettled by remember { mutableStateOf(true) }

    BackHandler(enabled = !isFirstStep) {
        onIntent(Intent.BackRequested)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.prayerGradient)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(56.dp)
                    .padding(horizontal = 8.dp)
            ) {
                if (!isFirstStep) {
                    IconButton(
                        onClick = { onIntent(Intent.BackRequested) },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
            val stepTransition = updateTransition(targetState = state.step, label = "onboardingStep")
            isStepSettled = !stepTransition.isRunning

            stepTransition.AnimatedContent(
                transitionSpec = {
                    val direction = (if (state.isMovingForward) 1 else -1) * (if (isRtl) -1 else 1)
                    (slideInHorizontally { it * direction } + fadeIn()) togetherWith
                            (slideOutHorizontally { -it * direction } + fadeOut())
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { step ->
                OnboardingStepBody(
                    step = step,
                    state = state,
                    onIntent = onIntent,
                    onOpenSelector = { activeSelector = it }
                )
            }

            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 20.dp, bottom = 12.dp)
                        .navigationBarsPadding()
                ) {
                    OnboardingPageIndicator(
                        pageCount = StepUi.entries.size,
                        currentPage = state.step.ordinal
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onIntent(Intent.PrimaryActionRequested) },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        enabled = !state.isLoading && isStepSettled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = stringResource(state.primaryAction.labelRes),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.height(48.dp)
                    ) {
                        if (state.canSkip && !state.isLoading) {
                            TextButton(
                                onClick = { onIntent(Intent.Skipped) },
                                enabled = isStepSettled
                            ) {
                                Text(
                                    text = stringResource(R.string.onboarding_not_now),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        activeSelector?.let { selector ->
            OnboardingSelectionSheet(
                selector = selector,
                state = state,
                onIntent = onIntent,
                onDismiss = { activeSelector = null }
            )
        }
    }
}

@Composable
private fun OnboardingStepBody(
    step: StepUi,
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit,
    onOpenSelector: (OnboardingContract.SelectorUi) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp)
    ) {
        OnboardingStepHeader(
            icon = step.icon,
            title = stringResource(step.titleRes),
            subtitle = stringResource(step.subtitleRes)
        )
        Spacer(modifier = Modifier.height(28.dp))
        when (step) {
            StepUi.WELCOME -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                SettingsCard {
                    SettingsSelectorRow(
                        icon = Icons.Default.Language,
                        title = stringResource(R.string.language),
                        valueLabel = stringResource(state.language.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.LANGUAGE) }
                    )
                    SettingsDivider()
                    SettingsSelectorRow(
                        icon = Icons.Default.DarkMode,
                        title = stringResource(R.string.theme),
                        valueLabel = stringResource(state.themeMode.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.THEME) }
                    )
                    SettingsDivider()
                    SettingsSelectorRow(
                        icon = Icons.Default.Schedule,
                        title = stringResource(R.string.time_format),
                        valueLabel = stringResource(state.timeFormat.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.TIME_FORMAT) }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.onboarding_change_later_hint),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.70f),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            StepUi.CONNECT -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                SettingsCard {
                    // ROW 1: Internet Status
                    SettingsSelectorRow(
                        icon = Icons.Default.Wifi,
                        title = stringResource(R.string.onboarding_internet_title),
                        valueLabel = if (state.isInternetConnected) {
                            stringResource(R.string.onboarding_internet_connected)
                        } else {
                            stringResource(R.string.onboarding_internet_action)
                        },
                        isEnabled = !state.isInternetConnected,
                        onClick = { onIntent(Intent.InternetActionRequested) }
                    )

                    SettingsDivider()

                    // ROW 2: Location Services & Permissions
                    SettingsSelectorRow(
                        icon = Icons.Default.LocationOn,
                        title = stringResource(R.string.access_location),
                        valueLabel = when {
                            state.isLocationReady ->
                                stringResource(R.string.onboarding_location_ready)
                            !state.areLocationServicesEnabled ->
                                stringResource(R.string.onboarding_open_settings)
                            state.isLocationPermanentlyDenied ->
                                stringResource(R.string.onboarding_open_settings)
                            else ->
                                stringResource(R.string.onboarding_location_action)
                        },
                        isEnabled = !state.isLocationReady,
                        onClick = { onIntent(Intent.LocationActionRequested) }
                    )
                }
            }

            StepUi.CALCULATION -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                SettingsCard {
                    SettingsSelectorRow(
                        icon = Icons.Default.Explore,
                        title = stringResource(R.string.onboarding_calculation_method_label),
                        valueLabel = stringResource(state.calculationMethod.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.CALCULATION_METHOD) }
                    )
                    SettingsDivider()
                    SettingsSelectorRow(
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        title = stringResource(R.string.asr_madhab),
                        valueLabel = stringResource(state.madhab.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.MADHAB) }
                    )
                }
            }

            StepUi.ALERTS -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingsCard {
                    SettingsSwitchRow(
                        icon = Icons.Default.NotificationsActive,
                        title = stringResource(R.string.onboarding_notifications_toggle_title),
                        subtitle = stringResource(R.string.onboarding_notifications_toggle_subtitle),
                        isChecked = state.notificationsEnabled && state.isNotificationPermissionGranted,
                        onCheckedChange = { onIntent(Intent.NotificationsToggled(it)) }
                    )
                    SettingsDivider()
                    SettingsSelectorRow(
                        icon = Icons.Default.MusicNote,
                        title = stringResource(R.string.adhan_sound),
                        valueLabel = stringResource(state.adhanSound.displayNameRes),
                        isEnabled = state.notificationsEnabled,
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.ADHAN_SOUND) }
                    )
                }

                if (state.notificationsEnabled &&
                    state.isNotificationPermissionGranted &&
                    !state.isExactAlarmGranted
                ) {
                    //Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.onboarding_exact_alarm_note),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(name = "Welcome", showSystemUi = true)
@Composable
private fun OnboardingWelcomePreview() {
    SalatiTheme {
        OnboardingContent(state = OnboardingContract.State(), onIntent = {})
    }
}