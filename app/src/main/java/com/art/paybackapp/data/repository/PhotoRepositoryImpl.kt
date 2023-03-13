package com.art.paybackapp.data.repository

import com.art.paybackapp.data.db.SimpleInMemoryDatabase
import com.art.paybackapp.domain.model.PhotoSearchDomainData

class PhotoRepositoryImpl(
    private val simpleInMemoryDatabase: SimpleInMemoryDatabase
) : PhotoRepository {

    override fun getLast(): PhotoSearchDomainData? {
        return simpleInMemoryDatabase.photoSearchData
    }

    override fun saveLast(photoSearchDomainData: PhotoSearchDomainData) {
        simpleInMemoryDatabase.photoSearchData = photoSearchDomainData
    }

}
