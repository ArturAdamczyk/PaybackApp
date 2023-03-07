package com.art.paybackapp.presentation.search

import android.content.res.Resources
import com.art.paybackapp.domain.model.PhotoDomainData
import com.art.paybackapp.domain.model.PhotoSearchEvent
import javax.inject.Inject

class PhotoSearchDisplayableFactory @Inject constructor(
    private val resources: Resources
) {

    fun create(photoSearchEvent: PhotoSearchEvent): PhotoSearchDisplayable {
        return PhotoSearchDisplayable(
            photos = photoSearchEvent.photoSearchDomainData.photos.map {
                createPhotoDisplayable(it)
            }
        )
    }

    private fun createPhotoDisplayable(photoDomainData: PhotoDomainData): PhotoDisplayable {
        return PhotoDisplayable(id = photoDomainData.id)
    }

}