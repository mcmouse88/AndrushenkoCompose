package com.mcmouse88.simple_counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mcmouse88.simple_counter.base.ColumnContainer
import com.mcmouse88.simple_counter.lists.SimpleList
import com.mcmouse88.simple_counter.ui.theme.SimpleCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCounterTheme {
                SimpleList()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ColumnContainer(name = "Buttons example") {
            ButtonsExample()
        }
        ColumnContainer(name = "TextField example") {
            TextFieldExample()
        }
        ColumnContainer(name = "CheckBox Example") {
            CheckBoxesExample()
        }
    }
}