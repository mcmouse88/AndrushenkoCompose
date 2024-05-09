package com.mcmouse88.constraint_layout.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.mcmouse88.constraint_layout.Rectangle

@Preview(showSystemUi = true)
@Composable
fun Example12DecoupledApi() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
        constraintSet = constraints()
    ) {

        Text(
            text = "Hello World 12345",
            color = Color.White,
            modifier = Modifier
                .layoutId("text1")
                .background(Color.Red)
                .padding(6.dp)
        )

        Spacer(modifier = Modifier.layoutId("spacer"))

        Text(
            text = "Lorem Ipsum dolor sit amet",
            color = Color.White,
            modifier = Modifier
                .layoutId("text2")
                .background(Color.Red)
                .padding(6.dp)
        )

        Rectangle(
            width = 10.dp,
            color = Color.Black,
            modifier = Modifier.layoutId("rectangle")
        )
    }
}

private fun constraints() = ConstraintSet {
    val text1 = createRefFor("text1")
    val text2 = createRefFor("text2")
    val spacer = createRefFor("spacer")
    val rectangle = createRefFor("rectangle")

    constrain(text1) {
        centerHorizontallyTo(parent)
    }

    constrain(spacer) {
        height = Dimension.value(10.dp)
    }

    constrain(text2) {
        centerHorizontallyTo(parent)
    }

    createVerticalChain(
        text1, text2, spacer,
        chainStyle = ChainStyle.Packed
    )

    val barrier = createEndBarrier(text1, text2)

    constrain(rectangle) {
        start.linkTo(barrier)
        centerVerticallyTo(parent)
    }
}