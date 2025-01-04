package com.mcmouse88.nav_component.screens.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcmouse88.nav_component.components.LoadResultContent
import com.mcmouse88.nav_component.model.LoadResult
import com.mcmouse88.nav_component.screens.ItemsGraph
import com.mcmouse88.nav_component.screens.LocalNavController
import com.mcmouse88.nav_component.screens.items.ItemsViewModel.ScreenState

@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = hiltViewModel()
) {
    val loadResult by viewModel.loadResultFlow.collectAsState()
    val navController = LocalNavController.current
    ItemsContent(getLoadResult = { loadResult }, onItemClicked = { index ->
        navController.navigate(ItemsGraph.EditItemRoute(index))
    })
}

@Composable
fun ItemsContent(
    getLoadResult: () -> LoadResult<ScreenState>, onItemClicked: (Int) -> Unit
) {
    LoadResultContent(
        loadResult = getLoadResult(),
        content = { screenState ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(screenState.items) { index, item ->
                    Text(text = item,
                        modifier = Modifier
                            .clickable { onItemClicked.invoke(index) }
                            .fillMaxWidth()
                            .padding(12.dp))
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsScreenPreview() {
    ItemsContent(getLoadResult = { LoadResult.Loading }, onItemClicked = {})
}