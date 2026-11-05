package com.mhq.salati.location.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.location.presentation.model.PrerequisitesUi

/** Everything still missing, in one place. Rows leave by themselves as each one is fixed. */
@Composable
fun PrerequisitesError(
    prerequisites: PrerequisitesUi,
    onEnableLocation: () -> Unit,
    onAllowPermission: () -> Unit,
    onOpenAppSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPermissionBlocked = prerequisites.permissionPermanentlyDenied

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.prerequisites_title),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        PrerequisiteRow(
            isMissing = !prerequisites.hasInternet,
            icon = Icons.Default.WifiOff,
            titleRes = R.string.prerequisite_internet,
            messageRes = R.string.prerequisite_internet_missing
        )
        PrerequisiteRow(
            isMissing = !prerequisites.servicesEnabled,
            icon = Icons.Default.LocationOff,
            titleRes = R.string.prerequisite_services,
            messageRes = R.string.prerequisite_services_missing,
            actionRes = R.string.prerequisite_action_enable_location,
            onAction = onEnableLocation
        )
        PrerequisiteRow(
            isMissing = !prerequisites.permissionGranted,
            icon = Icons.Default.Lock,
            titleRes = R.string.prerequisite_permission,
            messageRes = if (isPermissionBlocked) {
                R.string.prerequisite_permission_blocked
            } else {
                R.string.prerequisite_permission_missing
            },
            actionRes = if (isPermissionBlocked) {
                R.string.prerequisite_action_open_settings
            } else {
                R.string.prerequisite_action_allow
            },
            onAction = if (isPermissionBlocked) onOpenAppSettings else onAllowPermission
        )
    }
}

@Composable
private fun PrerequisiteRow(
    isMissing: Boolean,
    icon: ImageVector,
    @StringRes titleRes: Int,
    @StringRes messageRes: Int,
    @StringRes actionRes: Int? = null,
    onAction: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = isMissing,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(titleRes),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stringResource(messageRes),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (actionRes != null && onAction != null) {
                Spacer(Modifier.width(8.dp))
                Button(onClick = onAction) {
                    Text(stringResource(actionRes))
                }
            }
        }
    }
}