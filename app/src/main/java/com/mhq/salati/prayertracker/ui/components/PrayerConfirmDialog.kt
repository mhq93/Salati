package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mhq.salati.R
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun PrayerConfirmDialog(
    prayer: PrayerName,
    onConfirmPrayed: () -> Unit,
    onConfirmMissed: () -> Unit,
    onDismiss: () -> Unit
) {
    val prayerLabel = stringResource(prayer.labelRes)

    Dialog(onDismissRequest = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
        ) {
            Text(
                text = stringResource(R.string.did_you_pray_x, prayerLabel),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.mark_todays_x_prayer, prayerLabel),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DialogChoiceButton(
                    label = stringResource(R.string.prayed),
                    icon = Icons.Filled.Check,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onConfirmPrayed,
                    modifier = Modifier.weight(1f)
                )
                DialogChoiceButton(
                    label = stringResource(R.string.missed),
                    icon = Icons.Filled.Close,
                    color = MaterialTheme.colorScheme.error,
                    onClick = onConfirmMissed,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun PrayerConfirmDialogPreview() {
//    SalatiTheme() {
//        PrayerConfirmDialog(
//            prayer = PrayerName(
//                isMinorTiming = false
//            ),
//            onConfirmPrayed = {},
//            onConfirmMissed = {},
//            onDismiss = {}
//        )
//    }
//}