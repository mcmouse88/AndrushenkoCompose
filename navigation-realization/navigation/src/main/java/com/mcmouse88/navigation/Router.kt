package com.mcmouse88.navigation

import androidx.compose.runtime.Stable

@Stable
interface Router {

    /**
     * Launch a new screen amd place it at the top of screen stack
     */
    fun launch(route: Route)

    /**
     * Close the current screen and go to the previous one
     */
    fun pop(response: Any? = null)

    /**
     * Remove all screens from the navigation stack and launch the specified [route]
     */
    fun restart(route: Route) = restart(listOf(route))

    fun restart(rootRoutes: List<Route>, initialIndex: Int = 0)

    fun switchStack(index: Int)
}