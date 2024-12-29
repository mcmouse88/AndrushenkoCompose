package com.mcmouse88.nav_component.screens.add

import androidx.lifecycle.ViewModel
import com.mcmouse88.nav_component.model.ItemsRepository
import com.mcmouse88.nav_component.screens.action.ActionViewModel.Delegate
import com.mcmouse88.nav_component.screens.add.AddItemViewModel.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel(), Delegate<ScreenState, String> {

    override suspend fun loadState(): ScreenState {
        return ScreenState()
    }

    override fun showProgress(input: ScreenState): ScreenState {
        return input.copy(isProgressVisible = true)
    }

    override suspend fun execute(action: String) {
        itemsRepository.add(action)
    }

    data class ScreenState(
        val isProgressVisible: Boolean = false
    )
}