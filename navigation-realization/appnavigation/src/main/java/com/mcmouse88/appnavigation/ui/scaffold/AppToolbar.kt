package com.mcmouse88.appnavigation.ui.scaffold

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.mcmouse88.appnavigation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    @StringRes titleRes: Int,
    isRoot: Boolean,
    onPopAction: () -> Unit,
    onClearAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes),
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    if (!isRoot) onPopAction.invoke()
                }
            ) {
                Icon(
                    imageVector = if (isRoot) {
                        Icons.Default.Menu
                    } else {
                        Icons.AutoMirrored.Filled.ArrowBack
                    },
                    contentDescription = stringResource(id = R.string.main_menu)
                )
            }
        },
        actions = {
            var showPopup by remember { mutableStateOf(false) }
            val context = LocalContext.current

            IconButton(
                onClick = { showPopup = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.more_actions)
                )
            }

            DropdownMenu(
                expanded = showPopup,
                onDismissRequest = { showPopup = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.about)) },
                    onClick = {
                        Toast.makeText(
                            context,
                            R.string.app_name,
                            Toast.LENGTH_SHORT
                        ).show()
                        showPopup = false
                    }
                )

                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.clear)) },
                    onClick = {
                        onClearAction.invoke()
                        showPopup = false
                    }
                )
            }
        },
        modifier = modifier
    )
}