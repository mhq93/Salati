package com.mhq.salati.settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.settings.presentation.contract.SettingsContract.State
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSelectionSheet(
    state: State,
    selector: SelectorType,
    onSelect: (Intent) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        val (titleRes, options) = when (selector) {
            SelectorType.CalculationMethod -> R.string.calculation_method to CalculationMethod.entries
                .map {
                    Triple(it.displayNameRes, it == state.calculationMethod) { Intent.SelectCalculationMethod(it) }
                }

            SelectorType.Madhab -> R.string.asr_madhab to Madhab.entries
                .map { Triple(it.displayNameRes, it == state.madhab) { Intent.SelectMadhab(it) } }

            SelectorType.Theme -> R.string.theme to ThemeMode.entries
                .map { Triple(it.displayNameRes, it == state.themeMode) { Intent.SelectTheme(it) } }

            SelectorType.Language -> R.string.language to AppLanguage.entries
                .map { Triple(it.displayNameRes, it == state.language) { Intent.SelectLanguage(it) } }

            SelectorType.AdhanSound -> R.string.adhan_sound to AdhanSound.entries
                .map { Triple(it.displayNameRes, it == state.adhanSound) { Intent.SelectAdhanSound(it) } }
        }

        Column(
            modifier.padding(bottom = 24.dp)
        ) {
            Text(
                stringResource(titleRes),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            )

            LazyColumn {
                items(options) { (labelRes, isSelected, buildIntent) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(buildIntent()) }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 14.dp
                            )
                    ) {
                        Text(
                            text = stringResource(labelRes),
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.selected)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsSelectionSheetPreview() {
    SalatiTheme() {
        SettingsSelectionSheet(
            state = State(
                isLoading = true,
                notificationsEnabled = true,
                calculationMethod = CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY,
                madhab = Madhab.SHAFI,
                themeMode = ThemeMode.SYSTEM,
                language = AppLanguage.ENGLISH,
                adhanSound = AdhanSound.DEFAULT,
                hijriDateOffset = 0,
                appVersion = ""
            ),
            selector = SelectorType.CalculationMethod,
            onSelect = {},
            onDismiss = {}
        )
    }
}