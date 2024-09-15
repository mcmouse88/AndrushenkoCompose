package com.mcmouse88.drop_down_menu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mcmouse88.drop_down_menu.ui.theme.DropDownMenuTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDownMenuTheme {
                AppScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppScreen() {
    var showPopupMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.popup_menu_example))
        Spacer(modifier = Modifier.height(12.dp))

        Box {
            Button(
                onClick = {
                    showPopupMenu = true
                }
            ) {
                Text(text = stringResource(R.string.show))
            }

            DropdownMenu(
                expanded = showPopupMenu,
                onDismissRequest = { showPopupMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Menu Item 111") },
                    onClick = {
                        Toast.makeText(
                            context,
                            "Text 111",
                            Toast.LENGTH_SHORT
                        ).show()
                        showPopupMenu = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    },
                    contentPadding = PaddingValues(16.dp)
                )
                HorizontalDivider()
                DropdownMenuItem(
                    text = { Text(text = "Menu Item 222") },
                    onClick = {
                        Toast.makeText(
                            context,
                            "Text 222",
                            Toast.LENGTH_SHORT
                        ).show()
                        showPopupMenu = false
                    }
                )
            }
        }
    }
}