package com.mcmouse88.composition_local_sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mcmouse88.composition_local_sample.ui.theme.CompositionLocalSampleTheme

data class AppTheme(
    val buttonColor: Color,
    val bgColor: Color
) {
    companion object {
        val Light = AppTheme(
            buttonColor = Color.Blue,
            bgColor = Color.White
        )

        val Dark = AppTheme(
            buttonColor = Color.Gray,
            bgColor = Color.Black
        )
    }
}

val LocalAppTheme = compositionLocalOf {
    AppTheme.Light
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalSampleTheme {
                CompositionLocalProvider(
                    LocalAppTheme provides AppTheme.Dark
                ) {
                    AppScreen()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppScreen() {
    val context = LocalContext.current
    val theme = LocalAppTheme.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(theme.bgColor)
    ) {
        CompositionLocalProvider(
            LocalAppTheme provides theme.copy(buttonColor = Color.Red)
        ) {
            CustomButton(
                text = "Hello",
                onClick = {
                    Toast.makeText(
                        context,
                        "Hello World!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
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