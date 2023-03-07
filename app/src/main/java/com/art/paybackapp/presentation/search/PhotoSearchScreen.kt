package com.art.paybackapp.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PhotoSearchScreen(
    viewModel: PhotoSearchViewModel,
    onNavigate: (Int) -> (Unit)
) {

    when(val stateValue = viewModel.uiState.value){
        is PhotoSearchScreenState.Initial -> {
            viewModel.search("fruits")
        }
        is PhotoSearchScreenState.Loading -> {
            LoadingContent()
        }
        is PhotoSearchScreenState.ShowPhotos -> {
            ShowPhotosContent(
                data = stateValue.photoSearchDisplayable,
                onPickedPhoto = { onNavigate(it) }
            )
        }
        is PhotoSearchScreenState.Empty -> {
            EmptyContent()
        }
        is PhotoSearchScreenState.Error -> {
            ErrorContent()
        }
    }

}

@Composable
fun LoadingContent() {

}

@Composable
fun ShowPhotosContent(
    data: PhotoSearchDisplayable,
    onPickedPhoto: (Int) -> Unit = {}) {
}

@Composable
fun EmptyContent() {

}

@Composable
fun ErrorContent() {

}

@Composable
fun SearchBar() {

}
