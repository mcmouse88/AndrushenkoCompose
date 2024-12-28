package com.mcmouse88.nav_component.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.nav_component.model.ItemsRepository
import com.mcmouse88.nav_component.model.LoadResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditItemViewModel.Factory::class)
class EditItemViewModel @AssistedInject constructor(
    private val itemsRepository: ItemsRepository,
    @Assisted private val index: Int
) : ViewModel() {

    private val _loadResultFlow = MutableStateFlow<LoadResult<ScreenState>>(LoadResult.Loading)
    val loadResultFlow: StateFlow<LoadResult<ScreenState>> = _loadResultFlow.asStateFlow()

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    init {
        viewModelScope.launch {
            val loadingItem = itemsRepository.getByIndex(index)
            _loadResultFlow.tryEmit(LoadResult.Success(ScreenState(loadingItem)))
        }
    }

    fun update(newValue: String) {
        val loadResult = _loadResultFlow.value
        if (loadResult !is LoadResult.Success) return
        viewModelScope.launch {
            showProgress(loadResult)
            itemsRepository.update(index, newValue)
            goBack()
        }
    }

    private fun showProgress(loadResult: LoadResult.Success<ScreenState>) {
        val currentScreenState = loadResult.data
        val updatedScreenState = currentScreenState.copy(isEditInProgress = true)
        _loadResultFlow.tryEmit(LoadResult.Success(updatedScreenState))
    }

    private suspend fun goBack() {
        _exitChannel.send(Unit)
    }

    data class ScreenState(
        val loadedItem: String,
        val isEditInProgress: Boolean = false
    )

    @AssistedFactory
    interface Factory {
        fun create(index: Int): EditItemViewModel
    }
}