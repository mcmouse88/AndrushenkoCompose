package com.mcmouse88.nav_component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mcmouse88.nav_component.model.ItemsRepository
import com.mcmouse88.nav_component.screens.AddItemRoute
import com.mcmouse88.nav_component.screens.AppToolbar
import com.mcmouse88.nav_component.screens.ItemsRoute
import com.mcmouse88.nav_component.screens.LocalNavController
import com.mcmouse88.nav_component.screens.NavigateUpAction
import com.mcmouse88.nav_component.screens.add.AddItemScreen
import com.mcmouse88.nav_component.screens.items.ItemsScreen
import com.mcmouse88.nav_component.ui.theme.NavigationComponentTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ItemsRepository

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
    val titleRes = when (currentBackStackEntry?.destination?.route) {
        ItemsRoute -> R.string.items_screen
        AddItemRoute -> R.string.add_item_screen
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
            if (currentBackStackEntry?.destination?.route == ItemsRoute) {
                FloatingActionButton(
                    onClick = { navController.navigate(AddItemRoute) }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) { paddingValue ->
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = ItemsRoute,
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                composable(ItemsRoute) { ItemsScreen() }
                composable(AddItemRoute) { AddItemScreen() }
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