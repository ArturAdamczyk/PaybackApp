package com.art.paybackapp.presentation.screens.search.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.art.paybackapp.presentation.screens.search.PhotoDisplayable

@Composable
fun LazyPhotoList(photos: List<PhotoDisplayable>, onPicked: (Int) -> Unit = {}) {
    LazyColumn {
        items(photos) { item ->
            PhotoItem(item, onPicked)
        }
    }

}