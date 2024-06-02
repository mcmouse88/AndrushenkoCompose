package com.mcmouse88.appnavigation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mcmouse88.appnavigation.ui.scaffold.AppFloatingActionButton
import com.mcmouse88.appnavigation.ui.scaffold.AppNavigationBar
import com.mcmouse88.appnavigation.ui.scaffold.AppToolbar
import com.mcmouse88.navigation.rememberNavigation

@Composable
fun AppScaffold() {
    val navigation = rememberNavigation(initialRouter = AppRoute.Tab.Items)
    val (router, navigationState) = navigation

    Scaffold(
        topBar = {
            AppToolbar(
                navigationState = navigationState,
                router = router
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                navigationState = navigationState,
                router = router
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            AppNavigationBar(
                navigationState = navigationState,
                router = router
            )
        }
    ) { paddingValues ->
        AppNavigationHost(
            navigation = navigation,
            modifier = Modifier.padding(paddingValues)
        )
    }
}