package com.mhq.salati.onboarding.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.onboarding.presentation.components.OnboardingPageItem
import com.mhq.salati.onboarding.presentation.components.PageIndicator
import com.mhq.salati.onboarding.presentation.components.onboardingPages
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.shared.presentation.theme.AccentEmerald
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.DarkGreenLight
import com.mhq.salati.shared.presentation.theme.SheetBackground

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingContent(
    state: OnboardingContract.State,
    onIntent: (OnboardingContract.Intent) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { onboardingPages.size }
    )

    // Sync pager -> State (user swipe becomes an Intent, not local UI state)
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            onIntent(OnboardingContract.Intent.NextPage)
        }
    }

    // Sync State -> pager (e.g. programmatic advance from button tap)
    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkGreen, DarkGreenLight)
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Skip button
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
                            text = "Skip",
                            color = Color.White.copy(alpha = 0.8f),
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
                color = SheetBackground,
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
                        colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (state.currentPage == onboardingPages.lastIndex) "Get Started" else "Next",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}