package com.art.paybackapp.presentation.screens.search.ui.photos

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.art.paybackapp.presentation.screens.search.PhotoSearchDisplayable
import com.art.paybackapp.presentation.screens.search.ui.LazyPhotoList

@Composable
fun ShowPhotosContent(
    data: PhotoSearchDisplayable,
    onPickedPhoto: (Int) -> Unit = {}
) {
    Column {
        LazyPhotoList(data.photos, onPickedPhoto)
    }
}