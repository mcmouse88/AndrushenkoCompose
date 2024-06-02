package com.mcmouse88.appnavigation.ui.scaffold

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mcmouse88.appnavigation.ui.AppRoute
import com.mcmouse88.navigation.NavigationState
import com.mcmouse88.navigation.Router

/**
 * The list off all roo tabs
 */
val RootTabs = listOf(
    AppRoute.Tab.Items,
    AppRoute.Tab.Settings,
    AppRoute.Tab.Profile
)

@Composable
fun AppNavigationBar(
    navigationState: NavigationState,
    router: Router
) {
    if (navigationState.isRoot) {
        NavigationBar {
            RootTabs.forEach { tab ->
                NavigationBarItem(
                    selected = navigationState.currentRoute == tab,
                    label = { Text(text = stringResource(id = tab.titleRes)) },
                    onClick = { router.restart(tab) },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = stringResource(id = tab.titleRes)
                        )
                    }
                )
            }
        }
    }
}