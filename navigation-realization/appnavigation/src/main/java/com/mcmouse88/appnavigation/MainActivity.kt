package com.mcmouse88.appnavigation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.mcmouse88.appnavigation.screen.AddItemScreen
import com.mcmouse88.appnavigation.screen.ItemsScreen
import com.mcmouse88.appnavigation.screen.ProfileScreen
import com.mcmouse88.appnavigation.screen.SettingsScreen
import com.mcmouse88.appnavigation.ui.theme.NavigationTheme
import com.mcmouse88.navigation.NavigationHost
import com.mcmouse88.navigation.rememberNavigation

/**
 * The list off all roo tabs
 */
val RootTabs = listOf(
    AppRoute.Tab.Items,
    AppRoute.Tab.Settings,
    AppRoute.Tab.Profile
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                AppScaffold()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(itemsRepository: ItemsRepository = ItemsRepository.get()) {
    val navigation = rememberNavigation(initialRouter = AppRoute.Tab.Items)
    val (router, navigationState) = navigation

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = (navigationState.currentRoute as? AppRoute)?.titleRes
                                ?: R.string.app_name
                        ),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!navigationState.isRoot) router.pop()
                        }
                    ) {
                        Icon(
                            imageVector = if (navigationState.isRoot) {
                                Icons.Default.Menu
                            } else {
                                Icons.Default.ArrowBack
                            },
                            contentDescription = stringResource(id = R.string.main_menu)
                        )
                    }
                },
                actions = {
                    var showPopup by remember { mutableStateOf(false) }
                    val context = LocalContext.current

                    IconButton(
                        onClick = { showPopup = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.more_actions)
                        )
                    }

                    DropdownMenu(
                        expanded = showPopup,
                        onDismissRequest = { showPopup = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.about)) },
                            onClick = {
                                Toast.makeText(
                                    context,
                                    R.string.app_name,
                                    Toast.LENGTH_SHORT
                                ).show()
                                showPopup = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.clear)) },
                            onClick = {
                                itemsRepository.clear()
                                showPopup = false
                            }
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            if (navigationState.currentRoute == AppRoute.Tab.Items) {
                FloatingActionButton(
                    onClick = { router.launch(AppRoute.AddItem) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_new_item)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
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
    ) { paddingValues ->
        NavigationHost(
            navigation = navigation,
            modifier = Modifier.padding(paddingValues)
        ) { currentRoute ->
            when (currentRoute) {
                AppRoute.Tab.Items -> ItemsScreen()
                AppRoute.Tab.Settings -> SettingsScreen()
                AppRoute.Tab.Profile -> ProfileScreen()
                AppRoute.AddItem -> AddItemScreen()
            }
        }
    }
}