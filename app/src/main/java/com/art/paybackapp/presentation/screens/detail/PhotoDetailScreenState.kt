package com.art.paybackapp.presentation.screens.detail

import com.art.paybackapp.presentation.screens.detail.model.PhotoDetailDisplayable

sealed class PhotoDetailScreenState {
    object Initial : PhotoDetailScreenState()
    class ShowPhoto(val photoSearchDisplayable: PhotoDetailDisplayable) : PhotoDetailScreenState()
    object NoPhoto : PhotoDetailScreenState()
}