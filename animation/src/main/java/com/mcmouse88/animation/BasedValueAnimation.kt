package com.mcmouse88.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showSystemUi = true)
@Composable
fun BasedValueAnimation() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isVisible by remember {
            mutableStateOf(false)
        }
        var isTextExpanded by remember {
            mutableStateOf(false)
        }
        Button(
            onClick = { isVisible = !isVisible }
        ) {
            Text(text = "Toggle")
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = scaleIn() + expandVertically(),
            exit = scaleOut() + shrinkVertically()
        ) {
            val bgColor = if (isTextExpanded) Color.Red else Color.Yellow
            val animatedBgColor by animateColorAsState(
                targetValue = bgColor,
                label = "TextBackgroundAnimation"
            )

            Text(
                text = if (isTextExpanded) {
                    "Lorem Ipsum\nExpanded Text Example\nTest Test Test"
                } else {
                    "Collapsed"
                },
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable { isTextExpanded = !isTextExpanded }
                    .drawBehind { drawRect(animatedBgColor) }
                    .padding(16.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessMediumLow,
                            dampingRatio = Spring.DampingRatioHighBouncy
                        )
                    )
            )
        }
    }
}