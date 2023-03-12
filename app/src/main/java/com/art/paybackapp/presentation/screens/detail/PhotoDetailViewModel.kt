package com.art.paybackapp.presentation.screens.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.art.paybackapp.base.BaseViewModel
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoDomainData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val photoDomainEvents: PhotoDomainEvents,
    private val photoDetailDisplayableFactory: PhotoDetailDisplayableFactory,
) : BaseViewModel() {

    private val _uiState = mutableStateOf<PhotoDetailScreenState>(PhotoDetailScreenState.Initial)
    val uiState: State<PhotoDetailScreenState>
        get() = _uiState

    override fun bind() {
        // no-op
    }

    override fun unbind() {
        // no-op
    }

    fun loadPhoto(photoId: Int) {
        val photoDomainData = findPhoto(photoId)
        _uiState.value = PhotoDetailScreenState.ShowPhoto(
            photoDetailDisplayableFactory.create(photoDomainData!!)
        )
    }

    private fun findPhoto(photoId: Int): PhotoDomainData? {
        return photoDomainEvents
            .lastSearch()
            ?.photoSearchDomainData
            ?.photos
            ?.find {
                it.id == photoId
            }
    }

}