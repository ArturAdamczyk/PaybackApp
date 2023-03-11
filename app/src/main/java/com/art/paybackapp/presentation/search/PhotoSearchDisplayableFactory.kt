package com.art.paybackapp.presentation.search

import android.content.res.Resources
import com.art.paybackapp.R
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
        return PhotoDisplayable(
            id = photoDomainData.id,
            username = "${resources.getString(R.string.username)}: ${photoDomainData.user}",
            comments = "${resources.getString(R.string.comments)}: ${photoDomainData.comments}",
            tags = "${resources.getString(R.string.tags)}: ${photoDomainData.tags}",
            likes = "${resources.getString(R.string.likes)}: ${photoDomainData.likes}",
            downloads = "${resources.getString(R.string.downloads)}: ${photoDomainData.downloads}",
            imagePreviewUrl = photoDomainData.previewUrl,
            largeImageUrl = photoDomainData.largeImageUrl
        )
    }

}