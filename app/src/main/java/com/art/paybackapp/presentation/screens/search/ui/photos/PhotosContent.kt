package com.art.paybackapp.presentation.screens.search.ui.photos

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.art.paybackapp.presentation.screens.search.model.PhotoSearchDisplayable
import com.art.paybackapp.presentation.screens.search.ui.list.LazyPhotoList

@Composable
fun ShowPhotosContent(
    data: PhotoSearchDisplayable,
    onPickedPhoto: (Int) -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    Column {
        LazyPhotoList(
            data.photos,
            onPickedPhoto,
            onLoadMore
        )
    }
}