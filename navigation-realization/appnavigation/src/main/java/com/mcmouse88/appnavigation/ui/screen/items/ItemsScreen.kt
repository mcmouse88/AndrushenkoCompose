package com.mcmouse88.appnavigation.ui.screen.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
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
import com.mcmouse88.appnavigation.AppScreen
import com.mcmouse88.appnavigation.AppScreenEnvironment
import com.mcmouse88.appnavigation.FloatingAction
import com.mcmouse88.appnavigation.R
import com.mcmouse88.appnavigation.di.injectViewModel
import com.mcmouse88.appnavigation.ui.AppRoute
import com.mcmouse88.appnavigation.ui.screen.item.ItemScreenArgs
import com.mcmouse88.navigation.LocalRouter
import com.mcmouse88.navigation.ResponseListener
import com.mcmouse88.navigation.Router

val ItemsScreenProducer = { ItemsScreen() }

class ItemsScreen : AppScreen {

    private var router: Router? = null

    override val environment = AppScreenEnvironment().apply {
        titleRes = R.string.items
        icon = Icons.AutoMirrored.Filled.List
        floatingAction = FloatingAction(
            icon = Icons.Default.Add,
            onClick = {
                router?.launch(AppRoute.Item(ItemScreenArgs.Add))
            }
        )
    }

    @Composable
    override fun Content() {
        router = LocalRouter.current
        val viewModel = injectViewModel<ItemsViewModel>()
        val items by viewModel.itemsFlow.collectAsStateWithLifecycle()
        val isEmpty by remember { derivedStateOf { items.isEmpty() } }
        ResponseListener(viewModel::processResponse)
        ItemsContent(
            isItemsEmpty = isEmpty,
            items = { items },
            onItemClicked = {
                router?.launch(AppRoute.Item(ItemScreenArgs.Edit(it)))
            }
        )
    }
}

@Composable
fun ItemsContent(
    isItemsEmpty: Boolean,
    items: () -> List<String>,
    onItemClicked: (Int) -> Unit
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
            val itemList = items.invoke()
            items(itemList.size) { index ->
                Text(
                    text = itemList[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClicked(index)
                        }
                        .padding(all = 8.dp)
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
        items = { listOf("Item #1") },
        onItemClicked = {}
    )
}