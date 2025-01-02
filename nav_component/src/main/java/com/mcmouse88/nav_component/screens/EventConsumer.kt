package com.mcmouse88.nav_component.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.withContext

@Composable
fun <T> EventConsumer(
    channel: ReceiveChannel<T>,
    block: (T) -> Unit
) {
    val blockState by rememberUpdatedState(block)
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        withContext(Dispatchers.Main.immediate) {
            owner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                for (event in channel) blockState.invoke(event)
            }
        }
    }
}