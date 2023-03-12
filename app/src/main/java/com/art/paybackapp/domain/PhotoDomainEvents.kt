package com.art.paybackapp.domain

import com.art.paybackapp.common.lastValue
import com.art.paybackapp.domain.model.PhotoSearchEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class PhotoDomainEvents {

    internal var search: BehaviorSubject<PhotoSearchEvent> = BehaviorSubject.create()

    fun search(): Observable<PhotoSearchEvent> = search

    fun lastSearch(): PhotoSearchEvent? = search.lastValue

}