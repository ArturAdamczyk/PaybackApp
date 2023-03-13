package com.art.paybackapp.domain.model

data class PhotoSearchDomainData(
    val photosDomainData: PhotosDomainData = PhotosDomainData(photos = emptyList()),
    val searchPhrase: String = "",
    val currentPageNumber: Int = 1
)
