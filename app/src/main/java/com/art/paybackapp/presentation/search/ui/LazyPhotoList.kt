package com.art.paybackapp.presentation.search.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.art.paybackapp.presentation.search.PhotoDisplayable

@Composable
fun LazyPhotoList(photos: List<PhotoDisplayable>, onPicked: (Int) -> Unit = {}) {

    val _photos = remember { photos }

    LazyColumn {
        items(_photos){ item ->
            PhotoItem(item, onPicked)
        }
    }

}