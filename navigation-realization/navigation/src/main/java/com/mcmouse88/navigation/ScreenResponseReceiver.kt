package com.mcmouse88.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import kotlin.reflect.KClass

val LocalScreenResponseReceiver = staticCompositionLocalOf<ScreenResponseReceiver> {
    error("No ScreenResponseReceiver provides")
}

interface ScreenResponseReceiver {

    fun <T : Any> get(clazz: KClass<T>): T?
}

@Composable
inline fun<reified T : Any> ResponseListener(
    noinline block: (T) -> Unit
) {
    val receiver = LocalScreenResponseReceiver.current
    LaunchedEffect(Unit) {
        receiver.get(T::class)?.apply(block)
    }
}