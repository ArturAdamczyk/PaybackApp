package com.art.paybackapp.presentation.detail

import android.content.res.Resources
import com.art.paybackapp.domain.model.PhotoDomainData
import com.art.paybackapp.presentation.search.PhotoDisplayable
import javax.inject.Inject


class PhotoDetailDisplayableFactory @Inject constructor(
    private val resources: Resources
) {

    fun create(photoDomainData: PhotoDomainData) : PhotoDetailDisplayable {
        return PhotoDetailDisplayable(
            photo = PhotoDisplayable(
                id = photoDomainData.id
            )
        )
    }

}