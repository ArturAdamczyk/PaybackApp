package com.art.paybackapp.presentation.detail

import androidx.compose.runtime.Composable

@Composable
fun PhotoDetailScreen(
    viewModel: PhotoDetailViewModel,
    photoId: Int
) {

    when(val stateValue = viewModel.uiState.value){
        is PhotoDetailScreenState.Initial -> {
            viewModel.loadPhoto(photoId)
        }
        is PhotoDetailScreenState.ShowPhoto -> {
            ShowPhotoContent(stateValue.photoSearchDisplayable)
        }
    }

}

@Composable
fun ShowPhotoContent(photoDetailDisplayable: PhotoDetailDisplayable) {

}