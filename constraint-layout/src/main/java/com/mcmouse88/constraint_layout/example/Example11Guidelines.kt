package com.mcmouse88.constraint_layout.example

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mcmouse88.constraint_layout.Rectangle

@Preview(showSystemUi = true)
@Composable
fun Example11Guidelines() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val startGuideline = createGuidelineFromStart(32.dp)
        val endGuideline = createGuidelineFromEnd(32.dp)
        val topGuideline = createGuidelineFromTop(0.05f)
        val bottomGuideline = createGuidelineFromBottom(0.05f)

        Rectangle(
            color = Color.Blue,
            modifier = Modifier
                .constrainAs(createRef()) {
                    top.linkTo(topGuideline)
                    bottom.linkTo(bottomGuideline)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )
    }
}