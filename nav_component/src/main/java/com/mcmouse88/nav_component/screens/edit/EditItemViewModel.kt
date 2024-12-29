package com.mcmouse88.nav_component.screens.edit

import androidx.lifecycle.ViewModel
import com.mcmouse88.nav_component.model.ItemsRepository
import com.mcmouse88.nav_component.screens.action.ActionViewModel.Delegate
import com.mcmouse88.nav_component.screens.edit.EditItemViewModel.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = EditItemViewModel.Factory::class)
class EditItemViewModel @AssistedInject constructor(
    private val itemsRepository: ItemsRepository,
    @Assisted private val index: Int
) : ViewModel(), Delegate<ScreenState, String> {

    override suspend fun loadState(): ScreenState {
        return ScreenState(loadedItem = itemsRepository.getByIndex(index))
    }

    override fun showProgress(input: ScreenState): ScreenState {
        return input.copy(isEditInProgress = true)
    }

    override suspend fun execute(action: String) {
        itemsRepository.update(index, action)
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