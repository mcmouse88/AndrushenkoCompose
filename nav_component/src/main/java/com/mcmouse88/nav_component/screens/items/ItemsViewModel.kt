package com.mcmouse88.nav_component.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcmouse88.nav_component.model.ItemsRepository
import com.mcmouse88.nav_component.model.LoadResult
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

    val loadResultFlow: StateFlow<LoadResult<ScreenState>> = itemsRepository.getItems()
        .map { LoadResult.Success(ScreenState(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = LoadResult.Loading
        )

        data class ScreenState(val items: List<String>)
}