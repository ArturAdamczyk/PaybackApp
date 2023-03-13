package com.art.paybackapp.domain.model

import com.art.paybackapp.common.merge

object PhotoSearchDomainDataFactory {

    fun create(
        photosDomainData: PhotosDomainData,
        searchPhrase: String,
    ): PhotoSearchDomainData {
        return PhotoSearchDomainData(
            photosDomainData = photosDomainData,
            searchPhrase = searchPhrase
        )
    }

    fun create(
        photosDomainData: PhotosDomainData,
        lastSearch: PhotoSearchDomainData
    ): PhotoSearchDomainData {
        return PhotoSearchDomainData(
            photosDomainData = PhotosDomainData(
                merge(lastSearch.photosDomainData.photos, photosDomainData.photos)
                    .map {
                        it.clone()
                    }
            ),
            searchPhrase = lastSearch.searchPhrase,
            currentPageNumber = lastSearch.currentPageNumber + 1
        )
    }

}