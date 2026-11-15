package com.mhq.salati.onboarding.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Intent
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.GroupDivider
import com.mhq.salati.shared.ui.components.SunRingLoader

/**
 * The last onboarding step. Three requirements that stay on screen with a "Done" state (they do not
 * animate away, so the card never collapses to nothing), or the chosen city once one is picked.
 * While the first-launch warm-up runs, the card gives way to the sun ring.
 */
@Composable
fun LocationStepFields(
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit
) {
    val prerequisites = state.prerequisites

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        when {
            state.isPreparing -> {
                SunRingLoader(diameter = 140.dp)
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.loading_setting_up),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            state.hasSavedPlace -> GroupCard {
                ActionRow(
                    icon = Icons.Default.CheckCircle,
                    title = state.savedPlaceName ?: stringResource(R.string.onboarding_place_chosen),
                    valueLabel = stringResource(R.string.onboarding_place_change),
                    onClick = { onIntent(Intent.LocationPickerRequested) }
                )
            }

            prerequisites != null -> {
                val isPermissionBlocked = prerequisites.permissionPermanentlyDenied
                GroupCard {
                    RequirementRow(
                        icon = Icons.Default.Wifi,
                        title = stringResource(R.string.prerequisite_internet),
                        actionLabel = stringResource(R.string.prerequisite_internet_action),
                        isMet = prerequisites.hasInternet,
                        onClick = { onIntent(Intent.InternetSettingsRequested) }
                    )
                    GroupDivider()
                    RequirementRow(
                        icon = Icons.Default.MyLocation,
                        title = stringResource(R.string.prerequisite_services),
                        actionLabel = stringResource(R.string.action_open_settings),
                        isMet = prerequisites.servicesEnabled,
                        onClick = { onIntent(Intent.LocationSettingsRequested) }
                    )
                    GroupDivider()
                    RequirementRow(
                        icon = Icons.Default.Lock,
                        title = stringResource(R.string.prerequisite_permission),
                        actionLabel = stringResource(
                            if (isPermissionBlocked) {
                                R.string.action_open_settings
                            } else {
                                R.string.prerequisite_action_allow
                            }
                        ),
                        isMet = prerequisites.permissionGranted,
                        onClick = {
                            onIntent(
                                if (isPermissionBlocked) {
                                    Intent.AppSettingsRequested
                                } else {
                                    Intent.LocationPermissionRequested
                                }
                            )
                        }
                    )
                }
                TextButton(
                    onClick = { onIntent(Intent.LocationPickerRequested) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(text = stringResource(R.string.prerequisite_action_choose_manually))
                }
            }
        }
    }
}

/** One requirement: tappable while missing, a disabled "Done" row with a tick once met. */
@Composable
private fun RequirementRow(
    icon: ImageVector,
    title: String,
    actionLabel: String,
    isMet: Boolean,
    onClick: () -> Unit
) {
    ActionRow(
        icon = if (isMet) Icons.Default.CheckCircle else icon,
        title = title,
        valueLabel = if (isMet) stringResource(R.string.onboarding_requirement_done) else actionLabel,
        isEnabled = !isMet,
        onClick = onClick
    )
}