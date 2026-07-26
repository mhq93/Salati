package com.mhq.salati.prayertracker.presentation.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mhq.salati.prayertracker.domain.model.PrayerType
import com.mhq.salati.shared.presentation.theme.CardBackground
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.MutedSlate

@Composable
fun PrayerConfirmDialog(
    prayer: PrayerType,
    onConfirmPrayed: () -> Unit,
    onConfirmMissed: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(CardBackground)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Did you pray ${prayer.displayName}?",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = InkText
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Mark today's ${prayer.displayName} prayer",
                fontSize = 13.sp,
                color = MutedSlate
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DialogChoiceButton(
                    label = "Prayed",
                    icon = Icons.Filled.Check,
                    color = Color(0xFF1D9E75),
                    onClick = onConfirmPrayed,
                    modifier = Modifier.weight(1f)
                )
                DialogChoiceButton(
                    label = "Missed",
                    icon = Icons.Filled.Close,
                    color = Color(0xFFD84C3E),
                    onClick = onConfirmMissed,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}