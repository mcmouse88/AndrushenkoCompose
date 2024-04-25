package com.mcmouse88.simple_counter.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.mcmouse88.simple_counter.R
import com.mcmouse88.simple_counter.base.RowContainer

@Composable
fun ImagesExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        VectorIcon()
        MaterialIcon()
        TintedVectorIcon()
        IconWithModifier()
        SimpleImage()
        ContentScaleCropImage()
        SquareImage()
        ClippedImage()
        ClippedWithCustomShapeImage()
        SimpleAsyncLoadedImage()
        AsyncLoadedImage()

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun VectorIcon() {
    RowContainer(name = stringResource(R.string.simple_vector_icon)) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_beach_access_24),
            contentDescription = stringResource(R.string.umbrella_icon)
        )
    }
}

@Composable
fun MaterialIcon() {
    RowContainer(name = stringResource(R.string.material_icon)) {
        Icon(
            imageVector = Icons.Rounded.MailOutline,
            contentDescription = stringResource(R.string.material_mail_icon),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun TintedVectorIcon() {
    RowContainer(name = stringResource(R.string.tinted_vector_icon)) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_beach_access_24),
            contentDescription = stringResource(id = R.string.umbrella_icon),
            tint = Color.Red
        )
    }
}

@Composable
fun IconWithModifier() {
    RowContainer(name = stringResource(R.string.vector_icon_with_modifiers)) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_beach_access_24),
            contentDescription = stringResource(id = R.string.umbrella_icon),
            modifier = Modifier
                .size(48.dp)
                .background(Color.LightGray, CircleShape)
                .padding(6.dp)
        )
    }
}

@Composable
fun SimpleImage() {
    RowContainer(name = stringResource(R.string.simple_image)) {
        Image(
            painter = painterResource(id = R.drawable.img1),
            contentDescription = stringResource(R.string.random_architecture_showcase)
        )
    }
}

@Composable
fun ContentScaleCropImage() {
    RowContainer(name = stringResource(R.string.image_with_contentscale_crop)) {
        Image(
            painter = painterResource(id = R.drawable.img2),
            contentDescription = stringResource(id = R.string.random_architecture_showcase),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SquareImage() {
    RowContainer(name = stringResource(R.string.image_cropped_by_aspect_ratio)) {
        Image(
            painter = painterResource(id = R.drawable.img3),
            contentDescription = stringResource(id = R.string.random_architecture_showcase),
            contentScale = ContentScale.Crop,
            modifier = Modifier.aspectRatio(1f / 1f)
        )
    }
}

@Composable
fun ClippedImage() {
    RowContainer(name = stringResource(R.string.clipped_image)) {
        Image(
            painter = painterResource(id = R.drawable.img4),
            contentDescription = stringResource(id = R.string.random_architecture_showcase),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f / 1f)
                .clip(CircleShape)
        )
    }
}

@Composable
fun ClippedWithCustomShapeImage() {
    RowContainer(name = stringResource(R.string.clipped_image_with_custom_shape)) {
        Image(
            painter = painterResource(id = R.drawable.img5),
            contentDescription = stringResource(id = R.string.random_architecture_showcase),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f / 1f)
                .clip(
                    GenericShape { size, _ ->
                        moveTo(0f, size.height)
                        lineTo(size.width / 2, 0f)
                        lineTo(size.width, size.height)
                    }
                )
        )
    }
}

@Composable
fun SimpleAsyncLoadedImage() {
    RowContainer(name = stringResource(R.string.simple_async_load_by_coil)) {
        AsyncImage(
            model = "https://assets.bwbx.io/images/users/iqjWHBFdfxIU/iFIpP6jDizgg/v0/-1x-1.jpg",
            contentDescription = null
        )
    }
}

enum class RequestState {
    LOAD_NOT_REQUESTED,
    LOAD_REQUESTED
}

@Composable
fun AsyncLoadedImage() {
    var state by remember { mutableStateOf(RequestState.LOAD_NOT_REQUESTED) }

    RowContainer(name = stringResource(R.string.async_load_by_coil_with_state_tracking)) {
        when (state) {
            RequestState.LOAD_NOT_REQUESTED -> {
                LoadButton {
                    state = RequestState.LOAD_REQUESTED
                }
            }
            RequestState.LOAD_REQUESTED -> {
                SubcomposeAsyncImage(
                    model = "https://hollandgreen.b-cdn.net/app/uploads/2021/08/Pavilion-style-riverside-new-build-home.jpg",
                    contentDescription = null,
                    loading = {
                        CircularProgressIndicator()
                    },
                    error = {
                        Text(text = stringResource(R.string.load_failed), color = Color.Red)
                    }
                )
            }
        }
    }
}

@Composable
fun LoadButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = stringResource(R.string.load))
    }
}

