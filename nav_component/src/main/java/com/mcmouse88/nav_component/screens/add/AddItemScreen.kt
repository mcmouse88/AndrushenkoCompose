package com.mcmouse88.nav_component.screens.add

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
import com.mcmouse88.nav_component.screens.AddItemRoute
import com.mcmouse88.nav_component.screens.EventConsumer
import com.mcmouse88.nav_component.screens.LocalNavController
import com.mcmouse88.nav_component.screens.routeClass

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val screenState by viewModel.stateFlow.collectAsState()
    AddItemContent(
        screenState = screenState,
        onAddButtonClicked = viewModel::add
    )
    val navController = LocalNavController.current
    EventConsumer(channel = viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == AddItemRoute::class) {
            navController.navigateUp()
        }
    }
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