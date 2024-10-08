package com.mcmouse88.appnavigation.ui

import com.mcmouse88.appnavigation.AppScreen
import com.mcmouse88.appnavigation.ui.screen.item.ItemScreenArgs
import com.mcmouse88.appnavigation.ui.screen.items.ItemsScreenProducer
import com.mcmouse88.appnavigation.ui.screen.ProfileScreenProducer
import com.mcmouse88.appnavigation.ui.screen.SettingsScreenProducer
import com.mcmouse88.appnavigation.ui.screen.item.itemScreenProducer
import com.mcmouse88.navigation.Route
import kotlinx.parcelize.Parcelize

sealed class AppRoute(
    override val screenProducer: () -> AppScreen
) : Route {

    @Parcelize
    data class Item(
        val args: ItemScreenArgs
    ) : AppRoute(itemScreenProducer(args))

    sealed class Tab(
        screenProducer: () -> AppScreen
    ) : AppRoute(screenProducer) {
        @Parcelize data object Items : Tab(ItemsScreenProducer)
        @Parcelize data object Settings : Tab(SettingsScreenProducer)
        @Parcelize data object Profile : Tab(ProfileScreenProducer)
    }
}