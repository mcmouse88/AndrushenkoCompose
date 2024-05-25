package com.mcmouse88.immutable_stable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mcmouse88.immutable_stable.ui.theme.ImmutableStableTheme
import java.util.UUID

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImmutableStableTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    var label by remember { mutableStateOf("Hello") }
    var counter by remember { mutableIntStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        LabelText(label)
        CounterText(counter)
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = { counter++ }) {
            Text(
                text = stringResource(R.string.increment),
                fontSize = 18.sp
            )
        }
        
        Button(
            onClick = {
                label = UUID.randomUUID().toString().substringBefore("-")
            }
        ) {
            Text(
                text = stringResource(R.string.random_label),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun LabelText(label: String) {
    Text(
        text = label,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun CounterText(counter: Int) {
    Text(
        text = counter.toString(),
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )
}