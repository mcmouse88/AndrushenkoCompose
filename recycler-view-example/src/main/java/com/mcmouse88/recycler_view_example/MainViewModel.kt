package com.mcmouse88.recycler_view_example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository = MainRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(ScreenState())
    val state: StateFlow<ScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val items = mainRepository.getItems()
            _state.update { state ->
                state.copy(
                    list = items,
                    isLoading = false
                )
            }
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            _state.update { state -> updateInProgress(state, id) }
            mainRepository.deleteItem(id)
            _state.update { state -> deleteItemFromFlow(state, id) }
        }
    }

    private fun deleteItemFromFlow(state: ScreenState, id: Int): ScreenState {
        val mutable = state.list.toMutableList()
        val indexToDelete = mutable.indexOfFirst { it.id == id }
        if (indexToDelete == -1) return state
        mutable.removeAt(indexToDelete)
        return state.copy(list = mutable)
    }

    private fun updateInProgress(state: ScreenState, id: Int): ScreenState {
        val mutable = state.list.toMutableList()
        val indexToUpdate = mutable.indexOfFirst { it.id == id }
        if (indexToUpdate == -1) return state
        val temp = mutable[indexToUpdate]
        mutable[indexToUpdate] = temp.copy(inProgress = true)
        return state.copy(list = mutable)
    }

    data class ScreenState(
        val list: List<SimpleItem> = emptyList(),
        val isLoading: Boolean = true
    ) {
        val totalCount: Int get() = list.size
    }
}