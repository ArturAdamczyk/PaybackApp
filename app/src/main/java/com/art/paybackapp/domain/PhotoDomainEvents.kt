package com.art.paybackapp.domain

import com.art.paybackapp.common.lastValue
import com.art.paybackapp.domain.model.PhotoSearchEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class PhotoDomainEvents(
    private val photoSearchEventFactory: PhotoSearchEventFactory
) {

    internal val search: BehaviorSubject<PhotoSearchEvent> =
        BehaviorSubject.createDefault(initialState())

    fun search(): Observable<PhotoSearchEvent> = search

    fun lastSearch(): PhotoSearchEvent? = search.lastValue

    private fun initialState(): PhotoSearchEvent = photoSearchEventFactory.empty()

}