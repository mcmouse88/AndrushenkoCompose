package com.mcmouse88.nav_component.screens.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcmouse88.nav_component.R
import com.mcmouse88.nav_component.components.ItemDetails
import com.mcmouse88.nav_component.components.ItemDetailsState
import com.mcmouse88.nav_component.screens.action.ActionScreen

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    ActionScreen(
        delegate = viewModel,
        content = { (screenState, action) ->
            AddItemContent(
                screenState = screenState,
                onAddButtonClicked = action
            )
        }
    )
}

@Composable
fun AddItemContent(
    screenState: AddItemViewModel.ScreenState,
    onAddButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadItem = "",
            textFieldPlaceholder = stringResource(id = R.string.enter_new_item),
            actionButtonText = stringResource(id = R.string.add),
            isActionInProgress = screenState.isProgressVisible
        ),
        onActionButtonClick = onAddButtonClicked,
        modifier = Modifier.fillMaxSize()
    )
}