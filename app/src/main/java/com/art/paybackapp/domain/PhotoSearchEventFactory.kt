package com.art.paybackapp.domain

import com.art.paybackapp.domain.model.PhotoSearchDomainData
import com.art.paybackapp.domain.model.PhotoSearchEvent
import com.art.paybackapp.domain.model.PhotoSearchState

object PhotoSearchEventFactory{

    fun empty(): PhotoSearchEvent{
        return PhotoSearchEvent(
            photoSearchState = PhotoSearchState.Empty,
            photoSearchDomainData = PhotoSearchDomainData()
        )
    }

    fun error(): PhotoSearchEvent{
        return PhotoSearchEvent(
            photoSearchState = PhotoSearchState.Error,
            photoSearchDomainData = PhotoSearchDomainData()
        )
    }

    fun ready(photoSearchDomainData: PhotoSearchDomainData): PhotoSearchEvent{
        return PhotoSearchEvent(
            photoSearchState = PhotoSearchState.Ready,
            photoSearchDomainData = photoSearchDomainData
        )
    }

}