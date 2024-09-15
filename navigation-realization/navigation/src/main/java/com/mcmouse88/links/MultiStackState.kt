package com.mcmouse88.links

import com.mcmouse88.navigation.Route

data class MultiStackState(
    val stacks: List<StackState>,
    val activeStackIndex: Int
) {

    fun withNewRoutes(
        stackIndex: Int,
        route: Route
    ) : MultiStackState {
        val modifiedStacks = stacks.toMutableList()
        modifiedStacks[stackIndex] = modifiedStacks[stackIndex].withNewRoute(route)
        return copy(
            activeStackIndex = stackIndex,
            stacks = modifiedStacks
        )
    }
}
