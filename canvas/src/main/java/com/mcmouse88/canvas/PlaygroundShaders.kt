package com.mcmouse88.canvas

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun PlaygroundShaders(modifier: Modifier = Modifier) {
    val shader = RuntimeShader(SHADER_CODE)
    val brush = ShaderBrush(shader)
    val controller = rememberEasyCanvasController(
        initialContentRect = Rect(center = Offset.Zero, radius = 2.2f),
        initialContentScale = CanvasContentScale.CenterInside
    )
    EasyCanvas(controller = controller, modifier = modifier.sizeIn(200.dp, 200.dp)) {
        onDrawBehind {
            withContentTransform {
                drawRect(
                    brush = brush,
                    topLeft = contentRect.topLeft,
                    size = contentRect.size
                )
            }
        }
    }
}