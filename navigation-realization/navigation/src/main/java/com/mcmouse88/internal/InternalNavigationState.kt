package com.mcmouse88.internal

import com.mcmouse88.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow

internal sealed class NavigationEvent {
    data class Removed(val route: RouteRecord) : NavigationEvent()
}

internal interface InternalNavigationState {
    val currentUuid: String
    val screenResponseReceiver: ScreenResponseReceiver
    fun observe(): Flow<NavigationEvent>
}