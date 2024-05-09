package com.mcmouse88.constraint_layout.example

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.mcmouse88.constraint_layout.Square

@Preview(showSystemUi = true)
@Composable
fun Example08VisibilityAndGoneMargin() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            centerSquare
        ) = createRefs()

        Square(
            size = 50.dp,
            color = Color.Green,
            modifier = Modifier
                .constrainAs(createRef()) {
                    centerHorizontallyTo(centerSquare)
                    bottom.linkTo(centerSquare.top)
                }
        )

        Square(
            modifier = Modifier.constrainAs(centerSquare) {
                centerTo(parent)
                visibility = Visibility.Visible
            }
        )

        Square(
            size = 50.dp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(createRef()) {
                centerHorizontallyTo(centerSquare)
                top.linkTo(
                    centerSquare.bottom,
                    margin = 10.dp,
                    goneMargin = 50.dp
                )
            }
        )
    }
}