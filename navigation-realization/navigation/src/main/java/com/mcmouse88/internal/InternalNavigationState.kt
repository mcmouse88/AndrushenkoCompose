package com.mcmouse88.internal

import kotlinx.coroutines.flow.Flow

internal sealed class NavigationEvent {
    data class Removed(val route: RouteRecord) : NavigationEvent()
}

internal interface InternalNavigationState {
    val currentUuid: String
    fun observe(): Flow<NavigationEvent>
}