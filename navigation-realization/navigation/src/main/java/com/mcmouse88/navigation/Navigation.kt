package com.mcmouse88.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.mcmouse88.internal.ScreenStack

@Stable
data class Navigation(
    val router: Router,
    val navigationState: NavigationState
)

@Composable
fun rememberNavigation(initialRouter: Route): Navigation {
    return remember(initialRouter) {
        val screenStack = ScreenStack(mutableStateListOf(initialRouter))
        Navigation(
            router = screenStack,
            navigationState = screenStack
        )
    }
}
