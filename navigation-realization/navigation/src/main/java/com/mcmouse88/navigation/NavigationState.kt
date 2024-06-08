package com.mcmouse88.navigation

import androidx.compose.runtime.Stable

/**
 * Represents the current state of navigation.
 */
@Stable
interface NavigationState {

    /**
     * Whether there is only one screen in the stack.
     */
    val isRoot: Boolean

    /**
     * Current visible to the user screen (which is located at the top of screen stack).
     */
    val currentRoute: Route

    val currentScreen: Screen
}