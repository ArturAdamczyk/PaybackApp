package com.art.paybackapp.data.repository

import com.art.paybackapp.domain.model.PhotoSearchDomainData
import com.art.paybackapp.domain.model.PhotosDomainData
import io.reactivex.rxjava3.core.Single

interface PhotoRepository {

    fun lastSearch(): PhotoSearchDomainData?

    fun saveSearch(photoSearchDomainData: PhotoSearchDomainData)

    fun search(searchString: String, pageNumber: Int = 1) : Single<PhotosDomainData>

}