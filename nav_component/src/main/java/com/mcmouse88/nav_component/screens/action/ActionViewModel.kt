package com.mcmouse88.nav_component.screens.action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.nav_component.model.LoadResult
import com.mcmouse88.nav_component.model.tryUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActionViewModel<State, Action>(
    private val delegate: Delegate<State, Action>
) : ViewModel() {

    private val _loadResultFlow = MutableStateFlow<LoadResult<State>>(LoadResult.Loading)
    val loadResultFlow: StateFlow<LoadResult<State>> = _loadResultFlow.asStateFlow()

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _loadResultFlow.tryEmit(LoadResult.Loading)
            _loadResultFlow.value = try {
                LoadResult.Success(delegate.loadState())
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    fun execute(action: Action) {
        viewModelScope.launch {
            showProgress()
            try {
                delegate.execute(action)
                goBack()
            } catch (e: Exception) {
                hideProgress()
                _screenState.update { it.copy(error = e) }
            }
        }
    }

    fun onExitHandled() {
        _screenState.update { it.copy(exit = null) }
    }

    fun onErrorHandled() {
        _screenState.update { it.copy(error = null) }
    }

    private fun showProgress() {
        _loadResultFlow.tryUpdate(delegate::showProgress)
    }

    private fun hideProgress() {
        _loadResultFlow.tryUpdate(delegate::hideProgress)
    }

    private fun goBack() {
        _screenState.update { it.copy(exit = Unit) }
    }

    interface Delegate<State, Action> {
        suspend fun loadState(): State
        fun showProgress(input: State): State
        fun hideProgress(input: State): State
        suspend fun execute(action: Action)
    }

    data class ScreenState(
        val exit: Unit? = null,
        val error: Exception? = null
    )
}