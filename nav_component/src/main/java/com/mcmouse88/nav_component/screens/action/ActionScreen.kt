package com.mcmouse88.nav_component.screens.action

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mcmouse88.nav_component.components.ExceptionToMessageMapper
import com.mcmouse88.nav_component.components.LoadResultContent
import com.mcmouse88.nav_component.screens.LocalNavController
import com.mcmouse88.nav_component.screens.action.ActionViewModel.Delegate

data class ActionContentState<State, Action>(
    val state: State,
    val onExecuteAction: (Action) -> Unit
)

@Composable
fun <State, Action> ActionScreen(
    delegate: Delegate<State, Action>,
    content: @Composable (ActionContentState<State, Action>) -> Unit,
    modifier: Modifier = Modifier,
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper.Default
) {
    val viewModel = viewModel<ActionViewModel<State, Action>> {
        ActionViewModel(delegate)
    }
    val navController = LocalNavController.current
    val screenState by viewModel.screenState.collectAsStateWithLifecycle(
        minActiveState = Lifecycle.State.RESUMED
    )
    val context = LocalContext.current
    LaunchedEffect(key1 = screenState) {
        screenState.exit?.let {
            navController.navigateUp()
            viewModel.onExitHandled()
        }
    }

    LaunchedEffect(key1 = screenState) {
        screenState.error?.let { e ->
            val message = exceptionToMessageMapper.getUserMessage(e, context)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onErrorHandled()
        }
    }

    val loadResult by viewModel.loadResultFlow.collectAsState()
    LoadResultContent(
        loadResult = loadResult,
        content = { state ->
            val actionContentState = ActionContentState(
                state = state,
                onExecuteAction = viewModel::execute
            )
            content.invoke(actionContentState)
        },
        onTryAgainAction = viewModel::load,
        modifier = modifier
    )
}