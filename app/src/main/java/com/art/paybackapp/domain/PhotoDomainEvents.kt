package com.art.paybackapp.domain

import com.art.paybackapp.domain.model.PhotoSearchEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class PhotoDomainEvents {

    internal var search: PublishSubject<PhotoSearchEvent> = PublishSubject.create()

    fun search(): Observable<PhotoSearchEvent> = search

}