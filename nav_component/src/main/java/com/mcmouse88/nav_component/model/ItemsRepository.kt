package com.mcmouse88.nav_component.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class ItemsRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private val itemsFlow = MutableStateFlow(List(5) { "Item #${it + 1}" })

    suspend fun add(title: String) {
        delay(2.seconds)
        itemsFlow.update { it + title }
    }

    fun getItems(): Flow<List<String>> {
        return itemsFlow.onStart { delay(3.seconds) }
    }
}