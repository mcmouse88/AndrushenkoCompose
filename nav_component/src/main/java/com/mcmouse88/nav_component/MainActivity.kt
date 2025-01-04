package com.mcmouse88.nav_component

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mcmouse88.nav_component.screens.AppNavigationBar
import com.mcmouse88.nav_component.screens.AppToolbar
import com.mcmouse88.nav_component.screens.ItemsGraph
import com.mcmouse88.nav_component.screens.LocalNavController
import com.mcmouse88.nav_component.screens.MainTabs
import com.mcmouse88.nav_component.screens.NavigateUpAction
import com.mcmouse88.nav_component.screens.ProfileGraph
import com.mcmouse88.nav_component.screens.SettingsGraph
import com.mcmouse88.nav_component.screens.add.AddItemScreen
import com.mcmouse88.nav_component.screens.edit.EditItemScreen
import com.mcmouse88.nav_component.screens.items.ItemsScreen
import com.mcmouse88.nav_component.screens.profile.ProfileScreen
import com.mcmouse88.nav_component.screens.routeClass
import com.mcmouse88.nav_component.screens.settings.SettingsScreen
import com.mcmouse88.nav_component.ui.theme.NavigationComponentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationComponentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    NavApp(
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun NavApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val titleRes = when (currentBackStackEntry.routeClass()) {
        ItemsGraph.ItemsRoute::class -> R.string.items_screen
        ItemsGraph.AddItemRoute::class -> R.string.add_item_screen
        ItemsGraph.EditItemRoute::class -> R.string.edit_item_screen
        SettingsGraph.SettingsRoute::class -> R.string.settings_screen
        ProfileGraph.ProfileRoute::class -> R.string.profile_screen
        else -> R.string.app_name
    }
    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = titleRes,
                navigateUpAction = if (navController.previousBackStackEntry == null) {
                    NavigateUpAction.Hidden
                } else {
                    NavigateUpAction.Visible(
                        onClick = { navController.navigateUp() }
                    )
                }
            )
        },
        floatingActionButton = {
            if (currentBackStackEntry.routeClass() == ItemsGraph.ItemsRoute::class) {
                FloatingActionButton(
                    onClick = { navController.navigate(ItemsGraph.AddItemRoute) }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        },
        bottomBar = {
            AppNavigationBar(navController = navController, tabs = MainTabs)
        }
    ) { paddingValue ->
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            val intentHost = (LocalContext.current as? Activity)?.intent?.data?.host
            val startDestination: Any = when (intentHost) {
                "settings" -> SettingsGraph
                "items" -> ItemsGraph
                else -> ProfileGraph
            }
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                navigation<ItemsGraph>(startDestination = ItemsGraph.ItemsRoute) {
                    composable<ItemsGraph.ItemsRoute> { ItemsScreen() }
                    dialog<ItemsGraph.AddItemRoute> { AddItemScreen() }
                    composable<ItemsGraph.EditItemRoute>(
                        deepLinks = listOf(ItemsGraph.EditItemRoute.Link)
                    ) { entry ->
                        val route: ItemsGraph.EditItemRoute = entry.toRoute()
                        EditItemScreen(route.index)
                    }
                }
                navigation<SettingsGraph>(
                    startDestination = SettingsGraph.SettingsRoute,
                    deepLinks = listOf(SettingsGraph.Link)
                ) {
                    composable<SettingsGraph.SettingsRoute> { SettingsScreen() }
                }
                navigation<ProfileGraph>(startDestination = ProfileGraph.ProfileRoute) {
                    composable<ProfileGraph.ProfileRoute> { ProfileScreen() }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NavAppPreview() {
    NavigationComponentTheme {
        NavApp()
    }
}