package com.mcmouse88.simple_counter

import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.parcelize.Parcelize

@Composable
fun CheckBoxesExample() {
    val state = rememberSaveable(saver = CheckBoxState.Saver) {
        CheckBoxState(
            checkableItems = List(6) { index ->
                val id = index + 1
                CheckableItem(
                    title = "Item $id",
                    isChecked = mutableStateOf(false)
                )
            }
        )
    }
    state.checkableItems.forEach { checkedItem ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        val newCheckValue = !checkedItem.isChecked.value
                        checkedItem.isChecked.value = newCheckValue
                    },
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                )
        ) {
            Checkbox(
                checked = checkedItem.isChecked.value,
                onCheckedChange = {
                    val newCheckedValue = !checkedItem.isChecked.value
                    checkedItem.isChecked.value = newCheckedValue
                }
            )
            Text(text = checkedItem.title)
        }
    }
    Text(text = "Selected items: ${state.selectedItemNames}")
}

@Preview(showBackground = true)
@Composable
private fun CheckBoxesPreview() {
    Column {
        CheckBoxesExample()
    }
}

data class CheckableItem(
    val title: String,
    val isChecked: MutableState<Boolean>
)

data class CheckBoxState(
    val checkableItems: List<CheckableItem>
) {
    val selectedItemNames: String
        get() {
            return checkableItems.asSequence()
                .filter { it.isChecked.value }
                .map { it.title }
                .joinToString()
                .takeIf { it.isNotEmpty() } ?: "[nothing]"
        }

    companion object {
        val Saver: Saver<CheckBoxState, *> = Saver(
            save = { state: CheckBoxState ->
                ParcelableCheckBoxState(
                    checkedItems = state.checkableItems.map {
                        ParcelableCheckableItem(
                            it.title,
                            it.isChecked.value
                        )
                    }
                )
            },
            restore = { state: ParcelableCheckBoxState ->
                CheckBoxState(
                    checkableItems = state.checkedItems.map {
                        CheckableItem(
                            it.title,
                            mutableStateOf(it.isChecked)
                        )
                    }
                )
            }
        )
    }
}

@Parcelize
data class ParcelableCheckableItem(
    val title: String,
    val isChecked: Boolean
) : Parcelable

@Parcelize
data class ParcelableCheckBoxState(
    val checkedItems: List<ParcelableCheckableItem>
) : Parcelable