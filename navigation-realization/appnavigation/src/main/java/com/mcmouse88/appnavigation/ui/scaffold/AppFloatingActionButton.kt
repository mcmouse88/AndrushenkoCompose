package com.mcmouse88.appnavigation.ui.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mcmouse88.appnavigation.R
import com.mcmouse88.appnavigation.ui.AppRoute
import com.mcmouse88.navigation.NavigationState
import com.mcmouse88.navigation.Router

@Composable
fun AppFloatingActionButton(
    navigationState: NavigationState,
    router: Router
) {
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
}