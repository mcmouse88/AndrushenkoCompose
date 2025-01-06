package com.mcmouse88.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Scene {
    Button, Loading, Image
}

private val imageIds = intArrayOf(
    R.drawable.image1,
    R.drawable.image2,
    R.drawable.image3,
    R.drawable.image4,
    R.drawable.image5,
    R.drawable.image6
)

@Preview(showSystemUi = true)
@Composable
fun AnimatedContentAnimation() {
    var currentScene by remember {
        mutableStateOf(Scene.Button)
    }
    val scope = rememberCoroutineScope()
//    Crossfade(
    AnimatedContent(
        targetState = currentScene,
        label = "SwitchScene",
//        animationSpec = tween(1000) // for Crossfade
        transitionSpec = {
//            ContentTransform(
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(1000)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(1000)
            )
//            )
        }
    ) { scene ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (scene) {
                Scene.Button -> ButtonScene {
                    scope.launch {
                        currentScene = Scene.Loading
                        delay(2000)
                        currentScene = Scene.Image
                    }
                }

                Scene.Loading -> LoadingScene()
                Scene.Image -> ImageScene {
                    currentScene = Scene.Button
                }
            }
        }
    }
}

@Composable
fun ButtonScene(
    onStartLoading: () -> Unit
) {
    Button(
        onClick = onStartLoading
    ) {
        Text(text = "Load Random Image")
    }
}

@Composable
fun LoadingScene() {
    CircularProgressIndicator()
}

@Composable
fun ImageScene(
    onReset: () -> Unit
) {
    val randomImageId = remember {
        imageIds.random()
    }
    Image(
        painter = painterResource(id = randomImageId),
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onReset() }
    )
}