package com.mcmouse88.simple_counter.counter

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mcmouse88.simple_counter.logCompositionLifecycle

/**
 * Offset params calls only on `Layout` Compose Phase
 */
@Preview(showBackground = true)
@Composable
fun StatelessCounter(
    counterValue: Int = 0,
    onIncrement: (Int) -> Unit = {}
) {
    logCompositionLifecycle(name = "StatelessCounter")
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = counterValue.toString(),
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onIncrement.invoke(counterValue + 1)
            }
        ) {
            Text(text = "Increment", fontSize = 18.sp)
        }

        val offsetValue by remember {
            derivedStateOf { counterValue / 3 }
        }

        Text(
            text = "Test",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .size(width = 60.dp, height = 60.dp)
                .offset {
                    println("offset layout $counterValue")
                    IntOffset(x = 0, y = 20 * offsetValue)
                }
                .background(Color.Blue)
        )
    }
}

data class CounterState(
    val number: Int = 0
) : Parcelable {

    constructor(parcel: Parcel?) : this(parcel?.readInt() ?: 0)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(number)
    }

    companion object CREATOR : Parcelable.Creator<CounterState> {

        override fun createFromParcel(source: Parcel?): CounterState {
            return CounterState(source)
        }

        override fun newArray(size: Int): Array<CounterState?> {
            return arrayOfNulls(size)
        }
    }
}