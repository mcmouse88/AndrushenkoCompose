package com.mcmouse88.nav_component.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed class LoadResult<out T> {
    data object Loading : LoadResult<Nothing>()
    data class Success<T>(val data: T) : LoadResult<T>()
}

inline fun <T, R> LoadResult<T>.map(
    mapper: (T) -> R
): LoadResult<R> {
    return when (this) {
        LoadResult.Loading -> LoadResult.Loading
        is LoadResult.Success -> LoadResult.Success(mapper(data))
    }
}

inline fun <T> MutableStateFlow<LoadResult<T>>.tryUpdate(
    updater: (T) -> T
) {
    this.update { loadResult -> loadResult.map(updater) }
}