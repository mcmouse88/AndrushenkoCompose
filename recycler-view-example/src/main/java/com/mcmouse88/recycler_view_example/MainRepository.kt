package com.mcmouse88.recycler_view_example

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class MainRepository {

    private val itemList: MutableList<SimpleItem> = List(25) {
        SimpleItem(
            id = it,
            title = "List Item $it",
            inProgress = false
        )
    }.toMutableList()

    suspend fun getItems(): List<SimpleItem> {
        delay(3.seconds)
        return itemList
    }

    suspend fun deleteItem(id: Int) {
        delay(3.seconds)
        val indexToDelete = itemList.indexOfFirst { it.id == id }
        if (indexToDelete == -1) return
        itemList.removeAt(indexToDelete)
    }
}
