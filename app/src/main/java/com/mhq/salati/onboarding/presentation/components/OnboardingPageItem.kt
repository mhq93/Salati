package com.mhq.salati.onboarding.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingPageItem(page: OnboardingPageData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(140.dp)
                .background(
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f), // <-- Replaced Color.White.copy
                    CircleShape
                ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.18f), // <-- Replaced Color.White.copy
                        CircleShape
                    ),
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary, // <-- Replaced AccentGold
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = page.title,
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary // <-- Replaced Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.description,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f), // <-- Replaced Color.White.copy
            lineHeight = 22.sp
        )
    }
}

//package com.mhq.salati.onboarding.presentation.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.shared.presentation.theme.AccentGold
//
//@Composable
//fun OnboardingPageItem(page: OnboardingPageData) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 32.dp)
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .size(140.dp)
//                .background(
//                    Color.White.copy(alpha = 0.12f),
//                    CircleShape
//                ),
//        ) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .size(96.dp)
//                    .background(
//                        Color.White.copy(alpha = 0.18f),
//                        CircleShape
//                    ),
//            ) {
//                Icon(
//                    imageVector = page.icon,
//                    contentDescription = null,
//                    tint = AccentGold,
//                    modifier = Modifier.size(48.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(40.dp))
//
//        Text(
//            text = page.title,
//            textAlign = TextAlign.Center,
//            fontSize = 26.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Text(
//            text = page.description,
//            textAlign = TextAlign.Center,
//            fontSize = 15.sp,
//            color = Color.White.copy(alpha = 0.75f),
//            lineHeight = 22.sp
//        )
//    }
//}