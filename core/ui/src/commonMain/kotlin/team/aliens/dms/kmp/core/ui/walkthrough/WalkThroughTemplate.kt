package team.aliens.dms.kmp.core.ui.walkthrough

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

@Composable
fun <T> WalkThroughTemplate(
    modifier: Modifier = Modifier,
    state: WalkThroughState<T>,
    durationMillis: Int = 500,
    stepBuilder: WalkThroughStepBuilder<T>.() -> List<WalkThroughStepEntry<T>>,
) {
    val builder = WalkThroughStepBuilder<T>()
    val steps = stepBuilder(builder)

    LaunchedEffect(steps.size) {
        state.setStepProvider { steps }
    }

    val slideTweenSpec: TweenSpec<IntOffset> =
        tween(durationMillis = durationMillis, easing = LinearOutSlowInEasing)

    AnimatedContent(
        modifier = modifier,
        targetState = state.currentStep,
        transitionSpec = {
            if (targetState > initialState) {
                (fadeIn(tween(durationMillis)) + slideInHorizontally(slideTweenSpec) { width -> width }) togetherWith (
                        fadeOut(
                            tween(durationMillis),
                        ) + slideOutHorizontally(slideTweenSpec) { width -> -width }
                        )
            } else {
                (fadeIn(tween(durationMillis)) + slideInHorizontally(slideTweenSpec) { width -> -width }) togetherWith (
                        fadeOut(
                            tween(durationMillis),
                        ) + slideOutHorizontally(slideTweenSpec) { width -> width }
                        )
            }.using(SizeTransform(clip = false))
        },
    ) { index ->
        steps[index].content()
    }
}
