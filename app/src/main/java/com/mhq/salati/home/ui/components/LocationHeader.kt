package com.mhq.salati.home.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.prayerGradient

/** The city name with a pin beside it; tapping it opens the location picker. */
@Composable
fun LocationHeader(
    locationName: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f),
                shape = RoundedCornerShape(50)
            )
            .clickable(role = Role.Button, onClick = onClick)
            .padding(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = stringResource(R.string.cd_prayer_location),
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = locationName ?: stringResource(R.string.unknown_location),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp
        )
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Composable
private fun LocationHeaderLightPreview() {
    SalatiTheme(darkTheme = false) {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.prayerGradient)
                .padding(24.dp)
        ) {
            LocationHeader(locationName = "Alexandria, Egypt", onClick = {})
        }
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LocationHeaderDarkPreview() {
    SalatiTheme(darkTheme = true) {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.prayerGradient)
                .padding(24.dp)
        ) {
            LocationHeader(locationName = "Alexandria, Egypt", onClick = {})
        }
    }
}