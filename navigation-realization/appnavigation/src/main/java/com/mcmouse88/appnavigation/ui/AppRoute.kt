package com.mcmouse88.appnavigation.ui

import com.mcmouse88.appnavigation.AppScreen
import com.mcmouse88.appnavigation.ui.screen.AddItemScreenProducer
import com.mcmouse88.appnavigation.ui.screen.ItemsScreenProducer
import com.mcmouse88.appnavigation.ui.screen.ProfileScreenProducer
import com.mcmouse88.appnavigation.ui.screen.SettingsScreenProducer
import com.mcmouse88.navigation.Route
import kotlinx.parcelize.Parcelize

sealed class AppRoute(
    override val screenProducer: () -> AppScreen
) : Route {

    @Parcelize data object AddItem : AppRoute(AddItemScreenProducer)

    sealed class Tab(
        screenProducer: () -> AppScreen
    ) : AppRoute(screenProducer) {
        @Parcelize data object Items : Tab(ItemsScreenProducer)
        @Parcelize data object Settings : Tab(SettingsScreenProducer)
        @Parcelize data object Profile : Tab(ProfileScreenProducer)
    }
}