package com.art.paybackapp.data.repository

import com.art.paybackapp.data.db.SimpleInMemoryDatabase
import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.service.PhotoApi
import com.art.paybackapp.domain.model.PhotoSearchDomainData
import com.art.paybackapp.domain.model.PhotosDomainData
import io.reactivex.rxjava3.core.Single

class PhotoRepositoryImpl(
    private val simpleInMemoryDatabase: SimpleInMemoryDatabase,
    private val photoService: PhotoApi,
    private val photoSearchDtoMapper: PhotoSearchDtoMapper,
) : PhotoRepository {

    override fun lastSearch(): PhotoSearchDomainData? {
        return simpleInMemoryDatabase.photoSearchData
    }

    override fun saveSearch(photoSearchDomainData: PhotoSearchDomainData) {
        simpleInMemoryDatabase.photoSearchData = photoSearchDomainData
    }

    override fun search(
        searchString: String,
        pageNumber: Int
    ): Single<PhotosDomainData> {
        return photoService.search(
            searchString = searchString,
            pageNumber = pageNumber
        ).map { photoSearchDtoMapper.mapFrom(it) }
    }

}
