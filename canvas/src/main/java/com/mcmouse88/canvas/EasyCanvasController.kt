package com.mcmouse88.canvas

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Matrix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberEasyCanvasController(
    initialContentRect: Rect,
    initialContentScale: CanvasContentScale
): EasyCanvasController {
    val scope = rememberCoroutineScope()
    return remember(initialContentRect, initialContentScale) {
        EasyCanvasController(initialContentRect, initialContentScale, scope)
    }
}

class EasyCanvasController(
    initialContentRect: Rect,
    initialContentScale: CanvasContentScale,
    private val scope: CoroutineScope
) {
    val contentRect: Rect = initialContentRect
    val contentScale: CanvasContentScale = initialContentScale

    private var canvasFocus: CanvasFocus = CanvasFocus.Region(contentRect, contentScale)
    private var canvasSize: Size = Size.Unspecified

    private val contentMatrixAnimatable = Animatable(
        initialValue = Matrix(),
        typeConverter = MatrixTwoWayConverter
    )
    private val contentMatrixState = contentMatrixAnimatable.asState()
    val contentMatrix: Matrix get() = contentMatrixState.value

    val invertedContentMatrix: Matrix
        get() {
            return Matrix(contentMatrix.values.copyOf()).apply { invert() }
        }

    // get current scale
    val zoom: Float get() = contentMatrix.values[Matrix.ScaleX]

    // get current offset
    val offset: Offset get() = Offset(
        x = contentMatrix.values[Matrix.TranslateX],
        y = contentMatrix.values[Matrix.TranslateY]
    )

    fun panAndZoo(centroid: Offset, offsetDiff: Offset, zoomDiff: Float) {
        val newZoom = this.zoom * zoomDiff
        val newOffset = this.offset + offsetDiff

        val contentOffset = (centroid - newOffset) / this.zoom - (centroid - canvasSize.center) / newZoom
        setCanvasFocus(CanvasFocus.Point(contentOffset, newZoom))
    }

    fun focusOnViewPoint(offset: Offset, zoom: Float = this.zoom * 1.5f) {
        // Mapping the screen coordinates to the image coordinates
        val contentOffset = invertedContentMatrix.map(offset)
        setCanvasFocus(
            focus = CanvasFocus.Point(contentOffset, zoom),
            animate = true
        )
    }

    fun setCanvasSize(size: Size) {
        if (this.canvasSize != size) {
            this.canvasSize = size
            updateContentMatrix()
        }
    }

    private fun setCanvasFocus(focus: CanvasFocus, animate: Boolean = false) {
        if (this.canvasFocus != focus) {
            this.canvasFocus = focus
            updateContentMatrix(animate)
        }
    }

    private fun updateContentMatrix(animate: Boolean = false) {
        val canvasSize = this.canvasSize
        val canvasFocus = this.canvasFocus
        val newMatrix = if (canvasSize.isUnspecified) {
            Matrix()
        } else {
            when (canvasFocus) {
                is CanvasFocus.Point -> getContentMatrix(
                    canvasSize = canvasSize,
                    contentOffset = canvasFocus.contentOffset,
                    zoom = canvasFocus.zoom
                )

                is CanvasFocus.Region -> {
                    val contentRect = canvasFocus.contentRect
                    val contentScale = canvasFocus.contentScale
                    val zoom = calculateContentZoom(
                        canvasSize = canvasSize,
                        contentSize = contentRect.size,
                        canvasContentScale = contentScale
                    )
                    val contentOffset = contentRect.center
                    getContentMatrix(canvasSize, contentOffset, zoom)
                }
            }
        }
        scope.launch {
            if (animate) {
                contentMatrixAnimatable.animateTo(
                    targetValue = newMatrix,
                    animationSpec = tween(durationMillis = 1000)
                )
            } else {
                contentMatrixAnimatable.snapTo(newMatrix)
            }
        }
    }

    private fun getContentMatrix(canvasSize: Size, contentOffset: Offset, zoom: Float): Matrix {
       return Matrix().apply {
           translate(canvasSize.center.x, canvasSize.center.y)
           scale(zoom, zoom)
           translate(-contentOffset.x, -contentOffset.y)
       }
    }
}

private sealed class CanvasFocus {
    data class Region(
        val contentRect: Rect,
        val contentScale: CanvasContentScale
    ) : CanvasFocus()

    data class Point(
        val contentOffset: Offset,
        val zoom: Float
    ) : CanvasFocus()
}