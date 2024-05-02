package com.mcmouse88.composition_local_refresh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mcmouse88.composition_local_refresh.controller.AppThemeController
import com.mcmouse88.composition_local_refresh.controller.EmptyThemeController
import com.mcmouse88.composition_local_refresh.controller.RealThemeController
import com.mcmouse88.composition_local_refresh.data.AppTheme
import com.mcmouse88.composition_local_refresh.data.SharedPreferencesThemeDataStore

/**
 * Provides the current app theme.
 * @see AppTheme
 */
val LocalAppTheme = compositionLocalOf { AppTheme.Light }

/**
 * Provides the current app theme controller which can toggle an app theme.
 * @see AppThemeController
 */
val LocalAppThemeController = staticCompositionLocalOf<AppThemeController> {
    EmptyThemeController
}

/**
 * Base container for components which render itself by using app theme provides by
 * [LocalAppTheme] provider
 */
@Composable
fun AppThemeContainer(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val themeDataSource = remember {
        SharedPreferencesThemeDataStore(context)
    }

    val controller = remember {
        RealThemeController(themeDataSource)
    }

    val theme by themeDataSource.themeStateFlow.collectAsStateWithLifecycle()
    CompositionLocalProvider(
        LocalAppTheme provides theme,
        LocalAppThemeController provides controller
    ) {
        content.invoke()
    }
}