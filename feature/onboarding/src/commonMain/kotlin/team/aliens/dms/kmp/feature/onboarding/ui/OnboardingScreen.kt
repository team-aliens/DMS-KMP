package team.aliens.dms.kmp.feature.onboarding.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.onboarding.component.CompleteContent
import team.aliens.dms.kmp.feature.onboarding.component.IntroContent

@Composable
internal fun OnboardingScreen(
    navigateToSignIn: () -> Unit,
) {
    Onboarding(
        onCompleteClick = navigateToSignIn,
    )
}

@Composable
private fun Onboarding(
    onCompleteClick: () -> Unit,
) {
    var isLastStep by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = isLastStep,
            contentAlignment = Alignment.Center,
        ) { isLast ->
            if (isLast) {
                CompleteContent(onCompleteClick = onCompleteClick)
            } else {
                IntroContent(
                    onAnimatedEnt = { isLastStep = true },
                )
            }
        }
    }
}
