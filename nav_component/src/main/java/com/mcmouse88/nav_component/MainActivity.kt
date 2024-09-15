package com.mcmouse88.nav_component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mcmouse88.nav_component.screens.AddItemRoute
import com.mcmouse88.nav_component.screens.ItemsRoute
import com.mcmouse88.nav_component.screens.LocalNavController
import com.mcmouse88.nav_component.screens.add.AddItemScreen
import com.mcmouse88.nav_component.screens.items.ItemsScreen
import com.mcmouse88.nav_component.ui.theme.AndrushchenkoJetpackComposeCourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndrushchenkoJetpackComposeCourseTheme {
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
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = ItemsRoute,
            modifier = modifier.fillMaxSize()
        ) {
            composable(ItemsRoute) { ItemsScreen() }
            composable(AddItemRoute) { AddItemScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavAppPreview() {
    AndrushchenkoJetpackComposeCourseTheme {
        NavApp()
    }
}