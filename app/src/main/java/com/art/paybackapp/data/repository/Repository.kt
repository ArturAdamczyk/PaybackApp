package com.art.paybackapp.data.repository

import com.art.paybackapp.domain.model.PhotoSearchDomainData

interface PhotoRepository {

    fun getLast() : PhotoSearchDomainData?

    fun saveLast(photoSearchDomainData: PhotoSearchDomainData)

}