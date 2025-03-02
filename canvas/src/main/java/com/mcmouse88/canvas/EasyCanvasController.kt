package com.mcmouse88.canvas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Matrix

@Composable
fun rememberEasyCanvasController(
    initialContentRect: Rect,
    initialContentScale: CanvasContentScale
): EasyCanvasController {
    return remember(initialContentRect, initialContentScale) {
        EasyCanvasController(initialContentRect, initialContentScale)
    }
}

class EasyCanvasController(
    initialContentRect: Rect,
    initialContentScale: CanvasContentScale
) {
    val contentRect: Rect = initialContentRect
    val contentScale: CanvasContentScale = initialContentScale

    fun getContentMatrix(size: Size): Matrix {
       return Matrix().apply {
           translate(size.center.x, size.center.y)
           val zoom = calculateContentZoom(
               size,
               contentRect.size,
               contentScale
           )
           scale(zoom, zoom)
           translate(-contentRect.center.x, -contentRect.center.y)
       }
    }
}