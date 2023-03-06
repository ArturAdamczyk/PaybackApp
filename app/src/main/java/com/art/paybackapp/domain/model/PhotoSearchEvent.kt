package com.art.paybackapp.domain.model

class PhotoSearchEvent(
    val photoSearchState: PhotoSearchState,
    val photoSearchDomainData: PhotoSearchDomainData
)