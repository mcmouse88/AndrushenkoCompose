package com.mcmouse88.canvas

import androidx.compose.ui.geometry.Size
import kotlin.math.min
import kotlin.math.max

enum class CanvasContentScale {
    CenterInside, CenterCrop
}

fun calculateContentZoom(
    canvasSize: Size,
    contentSize: Size,
    canvasContentScale: CanvasContentScale
): Float {
    val horizontalZoom = canvasSize.width / contentSize.width
    val verticalZoom = canvasSize.height / contentSize.height
    return when (canvasContentScale) {
        CanvasContentScale.CenterInside -> min(horizontalZoom, verticalZoom)
        CanvasContentScale.CenterCrop -> max(horizontalZoom, verticalZoom)
    }
}