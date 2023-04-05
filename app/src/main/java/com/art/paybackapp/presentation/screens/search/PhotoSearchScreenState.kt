package com.art.paybackapp.presentation.screens.search

import com.art.paybackapp.presentation.screens.search.model.PhotoSearchDisplayable

sealed class PhotoSearchScreenState {
    object Initial : PhotoSearchScreenState()
    object Empty : PhotoSearchScreenState()
    object Loading : PhotoSearchScreenState()
    object Error : PhotoSearchScreenState()
    class ShowPhotos(val photoSearchDisplayable: PhotoSearchDisplayable) : PhotoSearchScreenState()
}
