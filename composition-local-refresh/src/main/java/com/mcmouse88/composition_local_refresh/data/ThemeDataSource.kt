package com.mcmouse88.composition_local_refresh.data

import kotlinx.coroutines.flow.StateFlow

/**
 * Store for the app theme. It holds the current app theme and allows changing it if needed.
 */
interface ThemeDataSource {

    /**
     * Observe the current app theme.
     * This flow emits a theme every time after updating it by [setTheme] call.
     */
    val themeStateFlow: StateFlow<AppTheme>

    /**
     * Update the current app theme. This call triggers updating of [themeStateFlow] if you pass
     * the [theme] which differs from the current theme.
     */
    fun setTheme(theme: AppTheme)
}