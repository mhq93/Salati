package com.mhq.salati.location.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.StepBadgeIcon
import com.mhq.salati.shared.ui.components.StepHeader
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.prayerGradient

/**
 * Everything still missing, in one place; rows leave by themselves as each one is fixed.
 * The user can always skip it by picking a place by hand.
 *
 * Fills the whole tab, behind the floating bottom bar; the content clears the bar itself.
 */
@Composable
fun PrerequisitesError(
    prerequisites: PrerequisitesUi,
    onOpenInternetSettings: () -> Unit,
    onOpenLocationSettings: () -> Unit,
    onAllowPermission: () -> Unit,
    onOpenAppSettings: () -> Unit,
    onChooseLocationManually: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPermissionBlocked = prerequisites.permissionPermanentlyDenied

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.prayerGradient)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(bottom = BottomNavBarDefaults.Height)
                .padding(horizontal = 32.dp)
        ) {
            StepHeader(
                title = stringResource(R.string.prerequisites_title),
                subtitle = stringResource(R.string.prerequisites_subtitle)
            ) {
                StepBadgeIcon(Icons.Default.LocationOn)
            }
            Spacer(Modifier.height(28.dp))

            GroupCard {
                PrerequisiteRow(
                    icon = Icons.Default.Wifi,
                    title = stringResource(R.string.prerequisite_internet),
                    actionLabel = stringResource(R.string.prerequisite_internet_action),
                    isMissing = !prerequisites.hasInternet,
                    showDivider = !prerequisites.servicesEnabled || !prerequisites.permissionGranted,
                    onClick = onOpenInternetSettings
                )
                PrerequisiteRow(
                    icon = Icons.Default.MyLocation,
                    title = stringResource(R.string.prerequisite_services),
                    actionLabel = stringResource(R.string.action_open_settings),
                    isMissing = !prerequisites.servicesEnabled,
                    showDivider = !prerequisites.permissionGranted,
                    onClick = onOpenLocationSettings
                )
                PrerequisiteRow(
                    icon = Icons.Default.Lock,
                    title = stringResource(R.string.prerequisite_permission),
                    actionLabel = stringResource(
                        if (isPermissionBlocked) {
                            R.string.action_open_settings
                        } else {
                            R.string.prerequisite_action_allow
                        }
                    ),
                    isMissing = !prerequisites.permissionGranted,
                    showDivider = false,
                    onClick = if (isPermissionBlocked) onOpenAppSettings else onAllowPermission
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = onChooseLocationManually,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(text = stringResource(R.string.prerequisite_action_choose_manually))
            }
        }
    }
}

@Preview(name = "All Missing", showBackground = true)
@Composable
private fun PrerequisitesErrorPreview() {
    SalatiTheme {
        PrerequisitesError(
            prerequisites = PrerequisitesUi(
                hasInternet = false,
                servicesEnabled = false,
                permissionGranted = false,
                permissionPermanentlyDenied = false
            ),
            onOpenInternetSettings = {},
            onOpenLocationSettings = {},
            onAllowPermission = {},
            onOpenAppSettings = {},
            onChooseLocationManually = {}
        )
    }
}

@Preview(name = "Blocked Permission", showBackground = true)
@Composable
private fun PrerequisitesErrorBlockedPreview() {
    SalatiTheme {
        PrerequisitesError(
            prerequisites = PrerequisitesUi(
                hasInternet = true,
                servicesEnabled = true,
                permissionGranted = false,
                permissionPermanentlyDenied = true
            ),
            onOpenInternetSettings = {},
            onOpenLocationSettings = {},
            onAllowPermission = {},
            onOpenAppSettings = {},
            onChooseLocationManually = {}
        )
    }
}