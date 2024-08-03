package com.mcmouse88.appnavigation.ui.screen.items

import androidx.lifecycle.ViewModel
import com.mcmouse88.appnavigation.ItemsRepository
import com.mcmouse88.appnavigation.ui.screen.item.ItemScreenArgs
import com.mcmouse88.appnavigation.ui.screen.item.ItemScreenResponse

class ItemsViewModel(
    private val repository: ItemsRepository = ItemsRepository.get()
) : ViewModel() {

    val itemsFlow = repository.getItems()

    fun processResponse(response: ItemScreenResponse) {
        when (response.args) {
            ItemScreenArgs.Add -> repository.addItem(response.newValue)
            is ItemScreenArgs.Edit -> repository.update(response.args.index, response.newValue)
        }
    }
}