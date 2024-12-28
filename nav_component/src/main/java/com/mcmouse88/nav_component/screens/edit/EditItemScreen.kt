package com.mcmouse88.nav_component.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcmouse88.nav_component.R
import com.mcmouse88.nav_component.components.ItemDetails
import com.mcmouse88.nav_component.components.ItemDetailsState
import com.mcmouse88.nav_component.components.LoadResultContent
import com.mcmouse88.nav_component.model.LoadResult
import com.mcmouse88.nav_component.screens.EditItemRoute
import com.mcmouse88.nav_component.screens.EventConsumer
import com.mcmouse88.nav_component.screens.LocalNavController
import com.mcmouse88.nav_component.screens.edit.EditItemViewModel.ScreenState
import com.mcmouse88.nav_component.screens.routeClass

@Composable
fun EditItemScreen(index: Int) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }

    val navController = LocalNavController.current
    EventConsumer(channel = viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == EditItemRoute::class) {
            navController.navigateUp()
        }
    }

    val loadResult by viewModel.loadResultFlow.collectAsState()
    EditItemContent(
        loadResult = loadResult,
        onEditButtonClicked = viewModel::update
    )
}

@Composable
fun EditItemContent(
    loadResult: LoadResult<ScreenState>,
    onEditButtonClicked: (String) -> Unit
) {
    LoadResultContent(
        loadResult = loadResult,
        content = { screenState ->
            SuccessItemContent(
                state = screenState,
                onEditButtonClicked = onEditButtonClicked
            )
        }
    )
}

@Composable
private fun SuccessItemContent(
    state: ScreenState,
    onEditButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadItem = state.loadedItem,
            textFieldPlaceholder = stringResource(id = R.string.edit_item_title),
            actionButtonText = stringResource(id = R.string.edit),
            isActionInProgress = state.isEditInProgress
        ),
        onActionButtonClick = onEditButtonClicked,
        modifier = Modifier.fillMaxSize()
    )
}