package com.art.paybackapp.presentation.screens.detail

sealed class PhotoDetailScreenState {
    object Initial : PhotoDetailScreenState()
    class ShowPhoto(val photoSearchDisplayable: PhotoDetailDisplayable) : PhotoDetailScreenState()
}