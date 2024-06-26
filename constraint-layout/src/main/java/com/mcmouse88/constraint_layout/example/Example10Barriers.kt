package com.mcmouse88.constraint_layout.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mcmouse88.constraint_layout.Rectangle

@Preview(showSystemUi = true)
@Composable
fun Example10Barriers() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            text1,
            space,
            text2
        ) = createRefs()

        createVerticalChain(
            text1, space, text2,
            chainStyle = ChainStyle.Packed
        )

        Text(
            text = "Hello World 12345",
            color = Color.White,
            modifier = Modifier
                .background(Color.Red)
                .padding(6.dp)
                .constrainAs(text1) {
                    centerHorizontallyTo(parent)
                }
        )

        Spacer(
            modifier = Modifier
                .constrainAs(space) {
                    height = Dimension.value(10.dp)
                }
        )

        Text(
            text = "Lorem Ipsum dolor sit amet",
            color = Color.White,
            modifier = Modifier
                .background(Color.Red)
                .padding(6.dp)
                .constrainAs(text2) {
                    centerHorizontallyTo(parent)
                }
        )

        val barrier = createEndBarrier(
            text1, text2
        )

        Rectangle(
            width = 10.dp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(createRef()) {
                    start.linkTo(barrier)
                    centerVerticallyTo(parent)
                }
        )
    }
}