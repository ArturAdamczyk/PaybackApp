package com.art.paybackapp.presentation.screens.search.ui.photos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.art.paybackapp.presentation.screens.search.PhotoSearchDisplayable
import com.art.paybackapp.presentation.screens.search.ui.SearchBar

@Composable
fun ShowPhotos(
    data: PhotoSearchDisplayable,
    searchTextState: MutableState<TextFieldValue>,
    onPickedPhoto: (Int) -> Unit = {},
    onSearch: (TextFieldValue) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp
            ),
    ) {
        Column {
            SearchBar(
                state = searchTextState,
                onSearch = onSearch
            )
            ShowPhotosContent(
                data = data,
                onPickedPhoto = onPickedPhoto
            )
        }
    }
}