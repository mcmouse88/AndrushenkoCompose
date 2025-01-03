package com.mcmouse88.dialogs

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mcmouse88.dialogs.ui.theme.DialogsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DialogsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DialogsApp(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun DialogsApp(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        var isDialogVisible by remember {
            mutableStateOf(false)
        }
        Button(
            onClick = {
                isDialogVisible = true
            }
        ) {
            Text(text = "Show Dialog")
        }
        if (isDialogVisible) {
            val context = LocalContext.current
            AlertDialog(
                onDismissRequest = { isDialogVisible = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            Toast.makeText(context, "Confirmed", Toast.LENGTH_SHORT).show()
                            isDialogVisible = false
                        }
                    ) {
                        Text(text = "Ok")
                    }
                },
                title = {
                    Text(text = "Test Dialog")
                },
                text = {
                    Text(text = "This is a test dialog.")
                }
            )
        }
    }
}