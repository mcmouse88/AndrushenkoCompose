package com.mcmouse88.interaction_source

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mcmouse88.interaction_source.ui.theme.InteractionSourceTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InteractionSourceTheme {
                CompositionLocalProvider(
                    LocalIndication provides CustomIndicationNodeFactory
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        /*val interactionSource = remember {
            MutableInteractionSource()
        }
        val isPressed by interactionSource.collectIsPressedAsState()
        val scaleFactor = if (isPressed) 0.7f else 1.0f
        val animatedScaleFactor by animateFloatAsState(
            targetValue = scaleFactor,
            label = "ScaleAnimation"
        )
        val indication = ripple(
            color = Color.Red,
            bounded = false,
            radius = 100.dp
        )*/
        Text(
            text = "Click Me!",
            fontSize = 20.sp,
            modifier = Modifier
                .clickable(
                    onClick = {
                        Toast
                            .makeText(context, "Clicked", Toast.LENGTH_SHORT)
                            .show()
                    },
                    // Without CompositionLocalProvider
                    /*interactionSource = interactionSource,
                    indication = CustomIndicationNodeFactory*/
                )
                .padding(16.dp)
                // Without custom modifier
                /*.drawWithContent {
                    scale(animatedScaleFactor) {
                        this@drawWithContent.drawContent()
                    }
                }*/
        )

        // Manual event to interaction source
        /*LaunchedEffect(key1 = Unit) {
            launch {
                delay(2000)
                val press = PressInteraction.Press(Offset.Zero)
                interactionSource.emit(press)
                delay(2000)
                interactionSource.emit(PressInteraction.Release(press))
            }
            interactionSource.interactions.collect { interaction ->
                Log.e("TAG_CHECK", "App: $interaction")
            }
        }*/
    }
}

data object CustomIndicationNodeFactory : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return CustomIndicationNode(interactionSource)
    }
}

private class CustomIndicationNode(
    private val interactionSource: InteractionSource
) : Modifier.Node(), DrawModifier {
    private val animatedScaleFactor = Animatable(1f)

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> animatedScaleFactor.animateTo(0.7f)
                    else -> animatedScaleFactor.animateTo(1.0f)
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        scale(animatedScaleFactor.value) {
           this@draw.drawContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    InteractionSourceTheme {
        App()
    }
}