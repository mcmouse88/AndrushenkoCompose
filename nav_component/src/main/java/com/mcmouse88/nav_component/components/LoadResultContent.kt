package com.mcmouse88.nav_component.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mcmouse88.nav_component.R
import com.mcmouse88.nav_component.model.DuplicateException
import com.mcmouse88.nav_component.model.LoadDataException
import com.mcmouse88.nav_component.model.LoadResult
import java.lang.Exception

@Composable
fun <T> LoadResultContent(
    loadResult: LoadResult<T>,
    content: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onTryAgainAction: () -> Unit = {},
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper.Default
) {
    when (loadResult) {
        LoadResult.Loading -> Box(modifier = modifier) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        is LoadResult.Success -> content.invoke(loadResult.data)
        is LoadResult.Error -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = exceptionToMessageMapper.getUserMessage(
                        exception = loadResult.exception,
                        context = LocalContext.current
                    ),
                    textAlign = TextAlign.Center
                )
                Button(onClick = onTryAgainAction) {
                    Text(text = stringResource(R.string.try_again))
                }
            }
        }
    }
}

fun interface ExceptionToMessageMapper {
    fun getUserMessage(exception: Exception, context: Context): String
    companion object {
        val Default = ExceptionToMessageMapper { exception, context ->
            when (exception) {
                is LoadDataException -> context.getString(R.string.failed_to_load_data)
                is DuplicateException -> context.getString(R.string.already_exist, exception.duplicatedValue)
                else -> context.getString(R.string.unknown_error)
            }
        }
    }
}