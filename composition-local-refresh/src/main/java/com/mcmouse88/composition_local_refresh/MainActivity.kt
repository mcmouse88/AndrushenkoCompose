package com.mcmouse88.composition_local_refresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mcmouse88.composition_local_refresh.ui.theme.CompositionLocalRefreshTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalRefreshTheme {
                AppThemeContainer {
                    AppScreen()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppScreen() {
    val theme = LocalAppTheme.current
    val themeController = LocalAppThemeController.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(theme.bgColor)
    ) {
        CustomButton(
            text = stringResource(id = R.string.change_app_theme),
            onClick = {
                themeController.toggle()
            }
        )
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalAppTheme.current
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = theme.buttonColor
        )
    ) {
        Text(text = text, color = Color.White)
    }
}
