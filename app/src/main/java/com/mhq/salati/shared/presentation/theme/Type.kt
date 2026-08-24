package com.mhq.salati.shared.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.Both
        ),
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
)

//val Typography = Typography(
//    displayLarge = TextStyle(/* ... */),
//    headlineLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Bold,
//        fontSize = 26.sp,
//        lineHeight = 32.sp,
//        letterSpacing = (-0.5).sp
//    ),
//    titleLarge = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 20.sp,
//        lineHeight = 26.sp
//    ),
//    bodyLarge = TextStyle(/* your existing */),
//    labelLarge = TextStyle(
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp
//    ),
//    // etc.
//)