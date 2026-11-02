package com.mhq.salati.settings.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.contract.SettingsContract.State
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.SelectionSheet
import com.mhq.salati.shared.ui.components.selectionOptions
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun SettingsSelectionSheet(
    state: State,
    selector: SelectorType,
    onSelect: (Intent) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (titleRes, options) = when (selector) {
        SelectorType.CalculationMethod -> R.string.calculation_method to selectionOptions(
            CalculationMethodUi.entries, state.calculationMethod, { it.displayNameRes }
        ) { onSelect(Intent.SelectCalculationMethod(it)) }

        SelectorType.Madhab -> R.string.asr_madhab to selectionOptions(
            MadhabUi.entries, state.madhab, { it.displayNameRes }
        ) { onSelect(Intent.SelectMadhab(it)) }

        SelectorType.Theme -> R.string.theme to selectionOptions(
            ThemeModeUi.entries, state.themeMode, { it.displayNameRes }
        ) { onSelect(Intent.SelectTheme(it)) }

        SelectorType.Language -> R.string.language to selectionOptions(
            AppLanguageUi.entries, state.language, { it.displayNameRes }
        ) { onSelect(Intent.SelectLanguage(it)) }

        SelectorType.AdhanSound -> R.string.adhan_sound to selectionOptions(
            AdhanSoundUi.entries, state.adhanSound, { it.displayNameRes }
        ) { onSelect(Intent.SelectAdhanSound(it)) }

        SelectorType.CustomAlarmSound -> R.string.custom_alarm_sound to selectionOptions(
            CustomAlarmSoundUi.entries, state.customAlarmSound, { it.displayNameRes }
        ) { onSelect(Intent.SelectCustomAlarmSound(it)) }

        SelectorType.TimeFormat -> R.string.time_format to selectionOptions(
            TimeFormatUi.entries, state.timeFormat, { it.displayNameRes }
        ) { onSelect(Intent.SelectTimeFormat(it)) }
    }

    SelectionSheet(
        titleRes = titleRes,
        options = options,
        onDismiss = onDismiss,
        modifier = modifier
    )
}

@Preview
@Composable
private fun SettingsSelectionSheetPreview() {
    SalatiTheme {
        SettingsSelectionSheet(
            state = State(
                isLoading = true,
                notificationsEnabled = true,
                calculationMethod = CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY,
                madhab = MadhabUi.SHAFI,
                themeMode = ThemeModeUi.SYSTEM,
                language = AppLanguageUi.ENGLISH,
                adhanSound = AdhanSoundUi.DEFAULT,
                hijriDateOffset = 0
            ),
            selector = SelectorType.CalculationMethod,
            onSelect = {},
            onDismiss = {}
        )
    }
}