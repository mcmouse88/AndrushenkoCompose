package com.mcmouse88.navigation

import androidx.compose.runtime.Stable

@Stable
interface NavigationState {
    val isRoot: Boolean
    val currentRoute: Route
}