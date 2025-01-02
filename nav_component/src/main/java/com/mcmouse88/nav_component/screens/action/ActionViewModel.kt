package com.mcmouse88.nav_component.screens.action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.nav_component.model.LoadResult
import com.mcmouse88.nav_component.model.tryUpdate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActionViewModel<State, Action>(
    private val delegate: Delegate<State, Action>
) : ViewModel() {

    private val _loadResultFlow = MutableStateFlow<LoadResult<State>>(LoadResult.Loading)
    val loadResultFlow: StateFlow<LoadResult<State>> = _loadResultFlow.asStateFlow()

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    private val _errorChannel = Channel<Exception>()
    val errorChannel: ReceiveChannel<Exception> = _errorChannel

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
                _errorChannel.send(e)
            }
        }
    }

    private fun showProgress() {
        _loadResultFlow.tryUpdate(delegate::showProgress)
    }

    private fun hideProgress() {
        _loadResultFlow.tryUpdate(delegate::hideProgress)
    }

    private suspend fun goBack() {
        _exitChannel.send(Unit)
    }

    interface Delegate<State, Action> {
        suspend fun loadState(): State
        fun showProgress(input: State): State
        fun hideProgress(input: State): State
        suspend fun execute(action: Action)
    }
}