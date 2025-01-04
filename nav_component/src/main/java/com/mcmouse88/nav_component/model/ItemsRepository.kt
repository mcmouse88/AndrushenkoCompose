package com.mcmouse88.nav_component.model

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class ItemsRepository @Inject constructor() {

    private val itemsFlow = MutableStateFlow(List(5) { "Item #${it + 1}" })

    suspend fun add(title: String) {
        delay(2.seconds)
        verifyNoDuplicates(title)
        itemsFlow.update { it + title }
    }

    fun getItems(): Flow<List<String>> {
        return itemsFlow.onStart { delay(3.seconds) }
    }

    suspend fun getByIndex(index: Int): String {
        delay(1.seconds)
        if (index == 0) throw LoadDataException()
        return itemsFlow.value[index]
    }

    suspend fun update(index: Int, newTitle: String) {
        delay(2.seconds)
        verifyNoDuplicates(newTitle, index)
        itemsFlow.update { oldList ->
            oldList.toMutableList().apply { set(index, newTitle) }
        }
    }

    private fun verifyNoDuplicates(title: String, index: Int = -1) {
        val duplicatedItemIndex = itemsFlow.value.indexOf(title)
        if (duplicatedItemIndex != -1 && duplicatedItemIndex != index) {
            throw DuplicateException(title)
        }
    }
}