package com.mcmouse88.appnavigation.ui.scaffold

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mcmouse88.appnavigation.ui.AppRoute
import com.mcmouse88.navigation.Route

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
    currentRoute: Route,
    onRouteSelected: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        RootTabs.forEach { tab ->
            val environment = remember(tab) {
                tab.screenProducer.invoke().environment
            }
            val icon = environment.icon
            if (icon != null) {
                NavigationBarItem(
                    selected = currentRoute == tab,
                    label = { Text(text = stringResource(id = environment.titleRes)) },
                    onClick = { onRouteSelected.invoke(tab) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(id = environment.titleRes)
                        )
                    }
                )
            }
        }
    }
}