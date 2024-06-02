package com.mcmouse88.appnavigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mcmouse88.appnavigation.ui.screen.AddItemScreen
import com.mcmouse88.appnavigation.ui.screen.ItemsScreen
import com.mcmouse88.appnavigation.ui.screen.ProfileScreen
import com.mcmouse88.appnavigation.ui.screen.SettingsScreen
import com.mcmouse88.navigation.Navigation
import com.mcmouse88.navigation.NavigationHost

@Composable
fun AppNavigationHost(
    navigation: Navigation,
    modifier: Modifier = Modifier
) {
    NavigationHost(
        navigation = navigation,
        modifier = modifier
    ) { currentRoute ->
        when (currentRoute) {
            AppRoute.Tab.Items -> ItemsScreen()
            AppRoute.Tab.Settings -> SettingsScreen()
            AppRoute.Tab.Profile -> ProfileScreen()
            AppRoute.AddItem -> AddItemScreen()
        }
    }
}