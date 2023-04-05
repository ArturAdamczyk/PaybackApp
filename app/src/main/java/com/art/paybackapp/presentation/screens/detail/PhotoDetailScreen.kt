package com.art.paybackapp.presentation.screens.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.art.paybackapp.presentation.screens.detail.ui.ShowPhoto

@Composable
fun PhotoDetailScreen(
    viewModel: PhotoDetailViewModel,
    photoId: Int
) {
    val viewState = viewModel.state().collectAsStateWithLifecycle().value
    viewModel.loadPhoto(photoId)

    when (viewState) {
        is PhotoDetailScreenState.Initial -> {}
        is PhotoDetailScreenState.NoPhoto -> {}
        is PhotoDetailScreenState.ShowPhoto -> {
            ShowPhoto(viewState.photoSearchDisplayable)
        }
    }

}