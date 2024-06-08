package com.mcmouse88.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.mcmouse88.internal.InternalNavigationState
import com.mcmouse88.internal.RouteRecord
import com.mcmouse88.internal.ScreenStack

@Stable
data class Navigation internal constructor(
    val router: Router,
    val navigationState: NavigationState,
    internal val internalNavigationState: InternalNavigationState
)

/**
 * Create and remember a new [Navigation] instance.
 * @param initialRouter starting a new screen to be displayed in the [NavigationHost]
 */
@Composable
fun rememberNavigation(initialRouter: Route): Navigation {

    val screenStack = rememberSaveable(initialRouter) {
        ScreenStack(mutableStateListOf(RouteRecord(initialRouter)))
    }

    return remember(initialRouter) {
        Navigation(
            router = screenStack,
            navigationState = screenStack,
            internalNavigationState = screenStack
        )
    }
}
