package com.mcmouse88.nav_component.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.withContext

@Composable
fun <T> EventConsumer(
    channel: ReceiveChannel<T>,
    block: (T) -> Unit
) {
    val blockState by rememberUpdatedState(block)
    LaunchedEffect(key1 = Unit) {
        withContext(Dispatchers.Main.immediate) {
            for (event in channel) blockState.invoke(event)
        }
    }
}