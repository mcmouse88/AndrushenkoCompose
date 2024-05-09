package com.mcmouse88.constraint_layout.example

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcmouse88.constraint_layout.Square

@Preview(showSystemUi = true)
@Composable
fun Example05MarginV2() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        Square(
            modifier = Modifier
                .constrainAs(createRef()) {
                    start.linkTo(parent.start, margin = 20.dp)
                    top.linkTo(parent.top, margin = 20.dp)
                }
        )

        Square(
            modifier = Modifier
                .constrainAs(createRef()) {
                    end.linkTo(parent.end, margin = 20.dp)
                    top.linkTo(parent.top, margin = 20.dp)
                }
        )

        Square(
            modifier = Modifier
                .constrainAs(createRef()) {
                    start.linkTo(parent.start, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                }
        )

        Square(
            modifier = Modifier
                .constrainAs(createRef()) {
                    end.linkTo(parent.end, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                }
        )
    }
}