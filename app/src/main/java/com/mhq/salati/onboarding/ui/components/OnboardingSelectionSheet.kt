package com.mhq.salati.onboarding.ui.components

import androidx.compose.runtime.Composable
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Intent
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.SelectorUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.SelectionSheet
import com.mhq.salati.shared.ui.components.selectionOptions

/** The choice sheets of onboarding; every pick applies live and closes the sheet. */
@Composable
fun OnboardingSelectionSheet(
    selector: SelectorUi,
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit,
    onDismiss: () -> Unit
) {
    fun pick(intent: Intent) {
        onIntent(intent)
        onDismiss()
    }

    val (titleRes, options) = when (selector) {
        SelectorUi.LANGUAGE -> R.string.language to selectionOptions(
            AppLanguageUi.entries, state.language, { it.displayNameRes }
        ) { pick(Intent.LanguageSelected(it)) }

        SelectorUi.THEME -> R.string.theme to selectionOptions(
            ThemeModeUi.entries, state.themeMode, { it.displayNameRes }
        ) { pick(Intent.ThemeSelected(it)) }

        SelectorUi.TIME_FORMAT -> R.string.time_format to selectionOptions(
            TimeFormatUi.entries, state.timeFormat, { it.displayNameRes }
        ) { pick(Intent.TimeFormatSelected(it)) }

        SelectorUi.CALCULATION_METHOD -> R.string.calculation_method to selectionOptions(
            CalculationMethodUi.entries, state.calculationMethod, { it.displayNameRes }
        ) { pick(Intent.CalculationMethodSelected(it)) }

        SelectorUi.MADHAB -> R.string.asr_madhab to selectionOptions(
            MadhabUi.entries, state.madhab, { it.displayNameRes }
        ) { pick(Intent.MadhabSelected(it)) }

        SelectorUi.ADHAN_SOUND -> R.string.adhan_sound to selectionOptions(
            AdhanSoundUi.entries, state.adhanSound, { it.displayNameRes }
        ) { pick(Intent.AdhanSoundSelected(it)) }
    }

    SelectionSheet(titleRes = titleRes, options = options, onDismiss = onDismiss)
}