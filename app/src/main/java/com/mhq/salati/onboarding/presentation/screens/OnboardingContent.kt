package com.mhq.salati.onboarding.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.components.OnboardingPageData
import com.mhq.salati.onboarding.presentation.components.OnboardingPageItem
import com.mhq.salati.onboarding.presentation.components.PageIndicator
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.prayerGradient

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingContent(
    state: OnboardingContract.State,
    onIntent: (OnboardingContract.Intent) -> Unit
) {
    val onboardingPages = listOf(
        OnboardingPageData(
            icon = Icons.Default.Schedule,
            title = stringResource(R.string.never_miss_a_prayer),
            description = stringResource(R.string.never_miss_a_prayer_subtitle)
        ),
        OnboardingPageData(
            icon = Icons.Default.LocationOn,
            title = stringResource(R.string.access_your_location),
            description = stringResource(R.string.access_your_location_subtitle)
        ),
        OnboardingPageData(
            icon = Icons.Default.Explore,
            title = stringResource(R.string.find_your_qibla),
            description = stringResource(R.string.find_your_qibla_subtitle)
        ),
        OnboardingPageData(
            icon = Icons.Default.NotificationsActive,
            title = stringResource(R.string.gentle_reminders),
            description = stringResource(R.string.gentle_reminders_subtitle)
        ),
        OnboardingPageData(
            icon = Icons.Default.Settings,
            title = stringResource(R.string.customize_your_app),
            description = stringResource(R.string.customize_your_app_subtitle)
        )
    )

    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { onboardingPages.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            onIntent(OnboardingContract.Intent.NextPage)
        }
    }

    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.prayerGradient) // <-- Replaced manual verticalGradient
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                if (state.currentPage < onboardingPages.lastIndex) {
                    TextButton(onClick = { onIntent(OnboardingContract.Intent.Skip) }) {
                        Text(
                            text = stringResource(R.string.skip),
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f), // <-- Replaced Color.White.copy
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageItem(page = onboardingPages[page])
            }

            // Bottom sheet with indicator + CTA
            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.surface, // <-- Replaced SheetBackground
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp, bottom = 32.dp)
                        .navigationBarsPadding(),
                ) {
                    PageIndicator(
                        pageCount = onboardingPages.size,
                        currentPage = pagerState.currentPage
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = {
                            if (state.currentPage == onboardingPages.lastIndex) {
                                onIntent(OnboardingContract.Intent.Finish)
                            } else {
                                onIntent(OnboardingContract.Intent.NextPage)
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary, // <-- Replaced AccentEmerald
                            contentColor = MaterialTheme.colorScheme.onPrimary    // <-- Replaced Color.White
                        ),
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary, // <-- Replaced Color.White
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (state.currentPage == onboardingPages.lastIndex)
                                    stringResource(R.string.get_started)
                                else
                                    stringResource(R.string.next),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimary // <-- Replaced Color.White
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
private fun OnboardingContentPreview() {
    SalatiTheme {
        OnboardingContent(
            state = OnboardingContract.State(),
            onIntent = {}
        )
    }
}

//package com.mhq.salati.onboarding.presentation.screens
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Explore
//import androidx.compose.material.icons.filled.NotificationsActive
//import androidx.compose.material.icons.filled.Schedule
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.R
//import com.mhq.salati.onboarding.presentation.components.OnboardingPageData
//import com.mhq.salati.onboarding.presentation.components.OnboardingPageItem
//import com.mhq.salati.onboarding.presentation.components.PageIndicator
//import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
//import com.mhq.salati.shared.presentation.theme.AccentEmerald
//import com.mhq.salati.shared.presentation.theme.DarkGreen
//import com.mhq.salati.shared.presentation.theme.DarkGreenLight
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//import com.mhq.salati.shared.presentation.theme.SheetBackground
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun OnboardingContent(
//    state: OnboardingContract.State,
//    onIntent: (OnboardingContract.Intent) -> Unit
//) {
//
//    val onboardingPages = listOf(
//        OnboardingPageData(
//            icon = Icons.Default.Schedule,
//            title = stringResource(R.string.never_miss_a_prayer),
//            description = stringResource(R.string.accurate_prayer_times_calculated_for_your_exact_location)
//        ),
//        OnboardingPageData(
//            icon = Icons.Default.Explore,
//            title = stringResource(R.string.find_your_qibla),
//            description = stringResource(R.string.a_precise_live_compass_points_you_toward_the_kaaba_wherever_you_are)
//        ),
//        OnboardingPageData(
//            icon = Icons.Default.NotificationsActive,
//            title = stringResource(R.string.gentle_reminders),
//            description = stringResource(R.string.custom_adhan_alerts_for_every_prayer_fully_in_your_control)
//        )
//    )
//
//    val pagerState = rememberPagerState(
//        initialPage = state.currentPage,
//        pageCount = { onboardingPages.size }
//    )
//
//    LaunchedEffect(pagerState.currentPage) {
//        if (pagerState.currentPage != state.currentPage) {
//            onIntent(OnboardingContract.Intent.NextPage)
//        }
//    }
//
//    LaunchedEffect(state.currentPage) {
//        if (pagerState.currentPage != state.currentPage) {
//            pagerState.animateScrollToPage(state.currentPage)
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(DarkGreen, DarkGreenLight)
//                )
//            )
//    ) {
//        Column(modifier = Modifier.fillMaxSize()) {
//
//            Row(
//                horizontalArrangement = Arrangement.End,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .statusBarsPadding()
//                    .padding(
//                        horizontal = 24.dp,
//                        vertical = 16.dp
//                    )
//            ) {
//                if (state.currentPage < onboardingPages.lastIndex) {
//                    TextButton(onClick = { onIntent(OnboardingContract.Intent.Skip) }) {
//                        Text(
//                            text = stringResource(R.string.skip),
//                            color = Color.White.copy(alpha = 0.8f),
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//                }
//            }
//
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier.weight(1f)
//            ) { page ->
//                OnboardingPageItem(page = onboardingPages[page])
//            }
//
//            // Bottom sheet with indicator + CTA
//            Surface(
//                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
//                color = SheetBackground,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .padding(horizontal = 24.dp)
//                        .padding(top = 32.dp, bottom = 32.dp)
//                        .navigationBarsPadding(),
//                ) {
//                    PageIndicator(
//                        pageCount = onboardingPages.size,
//                        currentPage = pagerState.currentPage
//                    )
//
//                    Spacer(modifier = Modifier.height(28.dp))
//
//                    Button(
//                        onClick = {
//                            if (state.currentPage == onboardingPages.lastIndex) {
//                                onIntent(OnboardingContract.Intent.Finish)
//                            } else {
//                                onIntent(OnboardingContract.Intent.NextPage)
//                            }
//                        },
//                        shape = RoundedCornerShape(16.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
//                        enabled = !state.isLoading,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp)
//                    ) {
//                        if (state.isLoading) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(20.dp),
//                                color = Color.White,
//                                strokeWidth = 2.dp
//                            )
//                        } else {
//                            Text(
//                                text = if (state.currentPage == onboardingPages.lastIndex)
//                                    stringResource(R.string.get_started)
//                                else
//                                    stringResource(R.string.next),
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun OnboardingContentPreview() {
//    SalatiTheme() {
//        OnboardingContent(
//            state = OnboardingContract.State(),
//            onIntent = {}
//        )
//    }
//}