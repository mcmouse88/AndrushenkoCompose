package com.mcmouse88.nav_component.screens.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcmouse88.nav_component.screens.EditItemRoute
import com.mcmouse88.nav_component.screens.LocalNavController

@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = hiltViewModel()
) {
    val screenState by viewModel.stateFlow.collectAsState()
    val navController = LocalNavController.current
    ItemsContent(
        getScreenState = { screenState },
        onItemClicked = { index ->
            navController.navigate(EditItemRoute(index))
        }
    )
}

@Composable
fun ItemsContent(
    getScreenState: () -> ItemsViewModel.ScreenState,
    onItemClicked: (Int) -> Unit
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
                    itemsIndexed(screenState.items) { index, item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .clickable { onItemClicked.invoke(index) }
                                .fillMaxWidth()
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsScreenPreview() {
    ItemsContent(
        getScreenState = { ItemsViewModel.ScreenState.Loading },
        onItemClicked = {}
    )
}