package com.mcmouse88.appnavigation.ui.scaffold

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mcmouse88.appnavigation.FloatingAction
import com.mcmouse88.appnavigation.R

/**
 * Floating Action Button from the right-bottom corner.
 * Now it is displayed only for ItemScreen.
 */
@Composable
fun AppFloatingActionButton(
    floatingAction: FloatingAction?,
    modifier: Modifier = Modifier
) {
    if (floatingAction != null) {
        FloatingActionButton(
            modifier = modifier,
            onClick = floatingAction.onClick
        ) {
            Icon(
                imageVector = floatingAction.icon,
                contentDescription = stringResource(id = R.string.add_new_item)
            )
        }
    }
}