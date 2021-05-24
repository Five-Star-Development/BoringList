package dev.five_star.boringlist.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SwipeDismissItem(
    background: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
    onDismissed: (isDismissed: Boolean) -> Unit,
) {
    val dismissState = rememberDismissState()
    val dismissedToEnd = dismissState.isDismissed(DismissDirection.StartToEnd)
    val dismissedToStart = dismissState.isDismissed(DismissDirection.EndToStart)

    Log.d(TAG, "dismissedToEnd = $dismissedToEnd")
    Log.d(TAG, "dismissedToStart = $dismissedToStart")

    var isDismissed = (dismissedToEnd || dismissedToStart)

    Log.d(TAG, "isDismissed = $isDismissed")

    onDismissed.invoke(isDismissed)
    // it looks like there is a runntime issue, because of that can hapoen the the dismissed state
    // is still the same for the next item. I am not sure where this issus comes from, there
    // it probably something wrong with the way I've implemented it
    isDismissed = false

    AnimatedVisibility(visible = !isDismissed) {
        SwipeToDismiss(
            state = dismissState,
            background = background,
            dismissContent = content,
        )
    }
}