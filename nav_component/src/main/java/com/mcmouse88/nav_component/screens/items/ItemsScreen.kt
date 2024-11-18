package com.mcmouse88.nav_component.screens.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcmouse88.nav_component.screens.AddItemRoute
import com.mcmouse88.nav_component.screens.LocalNavController

@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val screenState by viewModel.stateFlow.collectAsState()
    ItemsContent(
        getScreenState = { screenState },
        onLaunchAddItemScreen = {
            navController.navigate(AddItemRoute)
        }
    )
}

@Composable
fun ItemsContent(
    getScreenState: () -> ItemsViewModel.ScreenState,
    onLaunchAddItemScreen: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val screenState = getScreenState()) {
            ItemsViewModel.ScreenState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ItemsViewModel.ScreenState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(screenState.items) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = onLaunchAddItemScreen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsScreenPreview() {
    ItemsContent(
        getScreenState = { ItemsViewModel.ScreenState.Loading },
        onLaunchAddItemScreen = {}
    )
}