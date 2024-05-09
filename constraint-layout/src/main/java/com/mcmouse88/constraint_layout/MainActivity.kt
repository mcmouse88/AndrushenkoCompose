package com.mcmouse88.constraint_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcmouse88.constraint_layout.example.Example01Center
import com.mcmouse88.constraint_layout.ui.theme.ConstraintLayoutThemeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutThemeTheme {
                Example01Center()
            }
        }
    }
}