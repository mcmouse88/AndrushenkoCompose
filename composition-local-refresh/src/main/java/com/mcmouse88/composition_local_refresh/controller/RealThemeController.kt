package com.mcmouse88.composition_local_refresh.controller

import com.mcmouse88.composition_local_refresh.data.AppTheme
import com.mcmouse88.composition_local_refresh.data.ThemeDataSource

class RealThemeController(
    private val themeDataSource: ThemeDataSource
) : AppThemeController {

    override fun toggle() {
        val currentTheme = themeDataSource.themeStateFlow.value
        if (currentTheme == AppTheme.Dark) {
            themeDataSource.setTheme(AppTheme.Light)
        } else {
            themeDataSource.setTheme(AppTheme.Dark)
        }
    }
}