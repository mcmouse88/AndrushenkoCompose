package com.mcmouse88.appnavigation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mcmouse88.appnavigation.AppScreenEnvironment
import com.mcmouse88.appnavigation.ItemsRepository
import com.mcmouse88.appnavigation.ui.scaffold.AppFloatingActionButton
import com.mcmouse88.appnavigation.ui.scaffold.AppNavigationBar
import com.mcmouse88.appnavigation.ui.scaffold.AppToolbar
import com.mcmouse88.appnavigation.ui.scaffold.RootTabs
import com.mcmouse88.navigation.rememberNavigation

@Composable
fun AppScaffold() {
    val itemsRepository = ItemsRepository.get()
    val navigation = rememberNavigation(
        rootRoutes = RootTabs,
        deepLinkHandler = AppDeepLinkHandler
    )
    val (router, navigationState) = navigation
    val environment = navigationState.currentScreen.environment as AppScreenEnvironment

    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = environment.titleRes,
                isRoot = navigationState.isRoot,
                onPopAction = router::pop,
                onClearAction = itemsRepository::clear
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                floatingAction = environment.floatingAction
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            AppNavigationBar(
                currentIndex = navigationState.currentStackIndex,
                onIndexSelected = router::switchStack
            )
        }
    ) { paddingValues ->
        AppNavigationHost(
            navigation = navigation,
            modifier = Modifier.padding(paddingValues)
        )
    }
}