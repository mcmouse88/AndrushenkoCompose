package com.mcmouse88.composition_local_refresh.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Implementation of [ThemeDataSource] based on [SharedPreferences] so the current chosen theme
 * survives not only screen rotation but app death too.
 */
class SharedPreferencesThemeDataStore(
    context: Context
) : ThemeDataSource {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        THEME_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override val themeStateFlow: MutableStateFlow<AppTheme> = MutableStateFlow(readTheme())

    override fun setTheme(theme: AppTheme) {
        preferences.edit {
            putInt(KEY_BUTTON_COLOR, theme.buttonColor.toArgb())
            putInt(KEY_BACKGROUND_COLOR, theme.bgColor.toArgb())
        }
        themeStateFlow.value = theme
    }

    private fun readTheme(): AppTheme {
        if (!hasSavedTheme()) return AppTheme.Light
        return AppTheme(
            buttonColor = Color(preferences.getInt(KEY_BUTTON_COLOR, 0)),
            bgColor = Color(preferences.getInt(KEY_BACKGROUND_COLOR, 0))
        )
    }

    private fun hasSavedTheme(): Boolean = preferences.contains(KEY_BACKGROUND_COLOR)

    private companion object {
        const val THEME_PREFERENCES_NAME = "THEME_PREFERENCES_NAME"
        const val KEY_BUTTON_COLOR = "KEY_BUTTON_COLOR"
        const val KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR"
    }
}