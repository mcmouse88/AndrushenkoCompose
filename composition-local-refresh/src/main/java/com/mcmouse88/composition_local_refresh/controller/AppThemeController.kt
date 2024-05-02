package com.mcmouse88.composition_local_refresh.controller

import com.mcmouse88.composition_local_refresh.data.AppTheme

interface AppThemeController {

    /**
     * Toggle between Light/Dark app theme.
     * @see AppTheme
     */
    fun toggle()
}