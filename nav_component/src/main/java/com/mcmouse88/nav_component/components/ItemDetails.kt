package com.mcmouse88.nav_component.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ItemDetailsState(
    val loadItem: String,
    val textFieldPlaceholder: String,
    val actionButtonText: String,
    val isActionInProgress: Boolean
)

@Composable
fun ItemDetails(
    state: ItemDetailsState,
    modifier: Modifier = Modifier,
    onActionButtonClick: (String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var inputText by rememberSaveable { mutableStateOf(state.loadItem) }
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text(text = state.textFieldPlaceholder) },
            enabled = !state.isActionInProgress
        )

        Button(
            onClick = { onActionButtonClick(inputText) },
            enabled = !state.isActionInProgress
        ) {
            Text(text = state.actionButtonText)
        }

        Box(modifier = Modifier.size(32.dp)) {
            if (state.isActionInProgress) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }
        }
    }
}