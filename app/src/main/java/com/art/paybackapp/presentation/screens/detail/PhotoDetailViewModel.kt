package com.art.paybackapp.presentation.screens.detail

import com.art.paybackapp.common_android.base.BaseViewModel
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoDomainData
import com.art.paybackapp.presentation.screens.detail.model.PhotoDetailDisplayableFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val photoDomainEvents: PhotoDomainEvents,
    private val photoDetailDisplayableFactory: PhotoDetailDisplayableFactory,
) : BaseViewModel() {

    private val state = MutableStateFlow<PhotoDetailScreenState>(PhotoDetailScreenState.Initial)
    fun state(): StateFlow<PhotoDetailScreenState> = state

    private var photoId: Int = 0

    override fun bind() {
        // no-op
    }

    override fun unbind() {
        // no-op
    }

    fun loadPhoto(photoId: Int) {
        if (this.photoId != photoId) {
            val photoDomainData = findPhoto(photoId)
            photoDomainData?.let {
                photoFound(photoId, it)
            } ?: photoNotFound()
        }
    }

    private fun findPhoto(photoId: Int): PhotoDomainData? {
        return photoDomainEvents
            .lastSearch()
            ?.let{ photoSearchEvent ->
                photoSearchEvent.photoSearchDomainData.photosDomainData.photos.find {
                    it.id == photoId
                }
            }
    }

    private fun photoFound(
        photoId: Int,
        photoDomainData: PhotoDomainData
    ){
        state.value = PhotoDetailScreenState.ShowPhoto(
            photoDetailDisplayableFactory.create(photoDomainData)
        )
        this.photoId = photoId
    }

    private fun photoNotFound(){
        state.value = PhotoDetailScreenState.NoPhoto
    }

}