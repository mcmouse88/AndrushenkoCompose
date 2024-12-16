package com.mcmouse88.nav_component.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcmouse88.nav_component.R
import com.mcmouse88.nav_component.screens.EditItemRoute
import com.mcmouse88.nav_component.screens.EventConsumer
import com.mcmouse88.nav_component.screens.LocalNavController
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

    val screenState by viewModel.stateFlow.collectAsState()
    EditItemContent(
        state = screenState,
        onEditButtonClicked = viewModel::update
    )
}

@Composable
fun EditItemContent(
    state: EditItemViewModel.ScreenState,
    onEditButtonClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            EditItemViewModel.ScreenState.Loading -> CircularProgressIndicator()
            is EditItemViewModel.ScreenState.Success -> SuccessItemContent(state, onEditButtonClicked)
        }
    }
}

@Composable
private fun SuccessItemContent(
    state: EditItemViewModel.ScreenState.Success,
    onEditButtonClicked: (String) -> Unit
) {
    var inputText by rememberSaveable { mutableStateOf(state.loadedItem) }
    OutlinedTextField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = { Text(text = stringResource(R.string.edit_item_title)) },
        enabled = !state.isEditInProgress
    )

    Button(
        onClick = { onEditButtonClicked.invoke(inputText) },
        enabled = inputText.isNotBlank() && !state.isEditInProgress
    ) {
        Text(text = stringResource(R.string.edit))
    }

    Box(modifier = Modifier.size(32.dp)) {
        if (state.isEditInProgress) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
    }
}