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
import kotlinx.collections.immutable.persistentListOf

/**
 * The list off all roo tabs
 */
val RootTabs = persistentListOf(
    AppRoute.Tab.Items,
    AppRoute.Tab.Settings,
    AppRoute.Tab.Profile
)

/**
 * In-app bottom navigation bar
 */
@Composable
fun AppNavigationBar(
    currentIndex: Int,
    onIndexSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        RootTabs.forEachIndexed { index, tab ->
            val environment = remember(tab) {
                tab.screenProducer.invoke().environment
            }
            val icon = environment.icon
            if (icon != null) {
                NavigationBarItem(
                    selected = currentIndex == index,
                    label = { Text(text = stringResource(id = environment.titleRes)) },
                    onClick = { onIndexSelected.invoke(index) },
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