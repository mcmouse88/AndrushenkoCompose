package com.mcmouse88.constraint_layout.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mcmouse88.constraint_layout.Hint
import com.mcmouse88.constraint_layout.Rectangle

@Preview(showSystemUi = true)
@Composable
fun Example06LinksSize() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            startBound,
            endBound,
            rectFillToConstraints,
            rectMatchParent,
            rectPercentage,
            rectWrapContent,
            rectPreferredWrapContent,
            rectPreferredValue,
            rectAspectRation
        ) = createRefs()

        // --- bounds

        // start (left) bound
        Rectangle(
            width = 10.dp,
            height = 300.dp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(startBound) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, margin = 40.dp)
                }
        )

        // end (right) bound
        Rectangle(
            width = 10.dp,
            height = 300.dp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(endBound) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end, margin = 40.dp)
                }
        )

        // --- squares

        // fillToConstraints
        Hint(id = rectFillToConstraints, text = "fill to constraints")
        Rectangle(
            modifier = Modifier
                .constrainAs(rectFillToConstraints) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(startBound.top, margin = 10.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(20.dp)
                }
        )

        // matchParent
        Hint(id = rectMatchParent, text = "match parent")
        Rectangle(
            modifier = Modifier
                .constrainAs(rectMatchParent) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectFillToConstraints.bottom, margin = 30.dp)
                    width = Dimension.matchParent
                    height = Dimension.value(20.dp)
                }
        )

        // percentage
        Hint(id = rectPercentage, text = "percentage")
        Rectangle(
            modifier = Modifier
                .constrainAs(rectPercentage) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectMatchParent.bottom, margin = 30.dp)
                    width = Dimension.percent(0.7f)
                    height = Dimension.value(20.dp)
                }
        )

        // wrap content
        Hint(id = rectWrapContent, text = "wrap content")
        Text(
            text = "123",
            color = Color.White,
            modifier = Modifier
                .background(Color.Red)
                .padding(horizontal = 6.dp, vertical = 2.dp)
                .constrainAs(rectWrapContent) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectPercentage.bottom, margin = 30.dp)
                    width = Dimension.wrapContent
                }
        )

        // preferred wrap content
        Hint(id = rectPreferredWrapContent, text = "preferred wrap content")
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing",
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .background(Color.Red)
                .padding(horizontal = 6.dp, vertical = 2.dp)
                .constrainAs(rectPreferredWrapContent) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectWrapContent.bottom, margin = 30.dp)
                    width = Dimension.preferredWrapContent
                }
        )

        // preferred value
        Hint(id = rectPreferredValue, text = "preferred value")
        Rectangle(
            modifier = Modifier
                .constrainAs(rectPreferredValue) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectPreferredWrapContent.bottom, margin = 30.dp)
                    width = Dimension.preferredValue(200.dp)
                    height = Dimension.value(20.dp)
                }
        )

        // aspect ratio
        Hint(id = rectAspectRation, text = "aspect ratio")
        Rectangle(
            modifier = Modifier
                .constrainAs(rectAspectRation) {
                    start.linkTo(startBound.end)
                    end.linkTo(endBound.start)
                    top.linkTo(rectPreferredValue.bottom, margin = 30.dp)
                    width = Dimension.percent(0.25f)
                    height = Dimension.ratio("2:3")
                }
        )
    }
}