package com.mcmouse88.appnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcmouse88.appnavigation.ui.AppScaffold
import com.mcmouse88.appnavigation.ui.theme.NavigationTheme

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