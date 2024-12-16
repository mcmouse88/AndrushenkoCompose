package com.mcmouse88.nav_component.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.nav_component.model.ItemsRepository
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

    private val _stateFlow = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val stateFlow: StateFlow<ScreenState> = _stateFlow.asStateFlow()

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    init {
        viewModelScope.launch {
            val loadingItem = itemsRepository.getByIndex(index)
            _stateFlow.tryEmit(ScreenState.Success(loadingItem))
        }
    }

    fun update(newValue: String) {
        val currentState = _stateFlow.value
        if (currentState !is ScreenState.Success) return
        viewModelScope.launch {
            _stateFlow.tryEmit(currentState.copy(isEditInProgress = true))
            itemsRepository.update(index, newValue)
            _exitChannel.send(Unit)
        }
    }

    sealed interface ScreenState {
        data object Loading : ScreenState
        data class Success(
            val loadedItem: String,
            val isEditInProgress: Boolean = false
        ) : ScreenState
    }

    @AssistedFactory
    interface Factory {
        fun create(index: Int): EditItemViewModel
    }
}