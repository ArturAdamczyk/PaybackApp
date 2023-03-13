package com.art.paybackapp.presentation.screens.detail

import com.art.paybackapp.base.BaseViewModel
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoDomainData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val photoDomainEvents: PhotoDomainEvents,
    private val photoDetailDisplayableFactory: PhotoDetailDisplayableFactory,
) : BaseViewModel() {
    companion object {
        internal val name: String = PhotoDetailViewModel::class.java.name
    }

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
            state.value = PhotoDetailScreenState.ShowPhoto(
                photoDetailDisplayableFactory.create(photoDomainData!!)
            )
            this.photoId = photoId
        }
    }

    private fun findPhoto(photoId: Int): PhotoDomainData? {
        return photoDomainEvents
            .lastSearch()
            ?.photoSearchDomainData
            ?.photosDomainData
            ?.photos
            ?.find {
                it.id == photoId
            }
    }

}