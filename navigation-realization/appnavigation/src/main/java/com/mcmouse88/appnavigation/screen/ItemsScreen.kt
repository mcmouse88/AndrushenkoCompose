package com.mcmouse88.appnavigation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mcmouse88.appnavigation.ItemsRepository
import com.mcmouse88.appnavigation.R

@Composable
fun ItemsScreen() {
    val itemsRepository = ItemsRepository.get()
    val items by itemsRepository.getItems().collectAsStateWithLifecycle()
    val isEmpty by remember { derivedStateOf { items.isEmpty() } }
    ItemsContent(
        isItemsEmpty = isEmpty,
        items = { items }
    )
}

@Composable
fun ItemsContent(
    isItemsEmpty: Boolean,
    items: () -> List<String>
) {
    if (isItemsEmpty) {
        Text(
            text = stringResource(id = R.string.no_items),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.invoke()) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsContentPreview() {
    ItemsContent(
        isItemsEmpty = false,
        items = { listOf("Item #1") }
    )
}