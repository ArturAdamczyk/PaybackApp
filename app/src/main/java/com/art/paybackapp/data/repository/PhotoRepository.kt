package com.art.paybackapp.data.repository

import com.art.paybackapp.data.db.SimpleInMemoryDatabase
import com.art.paybackapp.domain.model.PhotoSearchDomainData

class PhotoRepository(
    private val simpleInMemoryDatabase: SimpleInMemoryDatabase
){

    fun getLast() : PhotoSearchDomainData? {
        return simpleInMemoryDatabase.photoSearchData
    }

    fun saveLast(photoSearchDomainData: PhotoSearchDomainData){
        simpleInMemoryDatabase.photoSearchData = photoSearchDomainData
    }

}
