package com.mcmouse88.links

import com.mcmouse88.navigation.Route

data class StackState(
    val routes: List<Route>
) {

    fun withNewRoute(route: Route): StackState = copy(
        routes = routes + route
    )
}
