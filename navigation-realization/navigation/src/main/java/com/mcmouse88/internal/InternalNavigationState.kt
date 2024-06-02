package com.mcmouse88.internal

import com.mcmouse88.navigation.Route
import kotlinx.coroutines.flow.Flow

internal sealed class NavigationEvent {
    data class Removed(val route: Route) : NavigationEvent()
}

internal interface InternalNavigationState {
    fun observe(): Flow<NavigationEvent>
}