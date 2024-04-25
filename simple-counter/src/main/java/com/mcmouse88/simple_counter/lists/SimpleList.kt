package com.mcmouse88.simple_counter.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showSystemUi = true)
@Composable
fun SimpleList() {

    val list = remember {
        List(100) { index ->
            "Simple Text Item ${index + 1}"
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        listHeader(text = "Test Header 111")

        items(list) { item ->
            ListItem(item = item)
        }

        listHeader(text = "Test Header 333")

        items(30) { index ->
            ListItem(item = "Qwerty ${index + 1}")
        }
    }
}

@Composable
private fun ListItem(item: String) {
    Text(
        text = item,
        modifier = Modifier.padding(all = 16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.listHeader(text: String) {
    stickyHeader {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(all = 16.dp)
        )
    }
}