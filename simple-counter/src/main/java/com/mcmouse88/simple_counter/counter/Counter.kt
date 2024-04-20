package com.mcmouse88.simple_counter.counter

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mcmouse88.simple_counter.logCompositionLifecycle

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
        Box(modifier = Modifier.height(100.dp)) {
            if (counterValue % 2 == 0) {
                logCompositionLifecycle(name = "IsEvenText")
                Text(text = "Is Even = true", fontSize = 18.sp)
            }
        }
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