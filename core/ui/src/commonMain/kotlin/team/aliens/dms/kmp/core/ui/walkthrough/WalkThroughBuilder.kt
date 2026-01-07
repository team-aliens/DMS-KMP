package team.aliens.dms.kmp.core.ui.walkthrough

import androidx.compose.runtime.Composable

data class WalkThroughStepEntry<T>(
    val type: T,
    val content: @Composable () -> Unit
)

class WalkThroughStepBuilder<T> {
    private val children: MutableList<WalkThroughStepEntry<T>> = mutableListOf()

    fun step(type: T, builder: @Composable () -> Unit): List<WalkThroughStepEntry<T>> {
        children.add(WalkThroughStepEntry(type, builder))
        return children
    }

    fun steps(
        types: List<T>,
        builder: @Composable (type: T) -> Unit
    ): List<WalkThroughStepEntry<T>> {
        types.mapTo(children) { type ->
            WalkThroughStepEntry(type) { builder(type) }
        }
        return children
    }

    internal fun build(): List<WalkThroughStepEntry<T>> = children
}
