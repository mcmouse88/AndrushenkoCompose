package com.mcmouse88.nav_component.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.nav_component.model.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    itemsRepository: ItemsRepository
) : ViewModel() {

    val stateFlow: StateFlow<ScreenState> = itemsRepository.getItems()
        .map(ScreenState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ScreenState.Loading
        )

    sealed interface ScreenState {
        data object Loading : ScreenState
        data class Success(val items: List<String>) : ScreenState
    }
}