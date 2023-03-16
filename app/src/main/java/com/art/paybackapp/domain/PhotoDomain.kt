package com.art.paybackapp.domain

import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.common.Bindable
import com.art.paybackapp.common.async
import com.art.paybackapp.data.repository.PhotoRepository
import com.art.paybackapp.domain.model.PhotoSearchDomainDataFactory
import com.art.paybackapp.domain.model.PhotoSearchDomainData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

class PhotoDomain(
    private val photoRepository: PhotoRepository,
    private val appSchedulers: AppSchedulers,
    private val photoDomainEvents: PhotoDomainEvents,
    private val photoSearchEventFactory: PhotoSearchEventFactory,
    private val photoDomainDataFactory: PhotoSearchDomainDataFactory
) : Bindable {

    private var compositeDisposable = CompositeDisposable()

    override fun bind() {
        // no-op
    }

    override fun unbind() {
        compositeDisposable.clear()
    }

    fun search(phrase: String) {
        photoRepository
            .search(phrase)
            .async(appSchedulers)
            .map { photoDomainDataFactory.create(it, phrase) }
            .subscribeBy(
                onSuccess = {
                    onSuccessSearch(it)
                },
                onError = {
                    onErrorSearch()
                }
            ).addTo(compositeDisposable)
    }

    fun searchMore() {
        val lastSearch = photoRepository.lastSearch()
        if (lastSearch != null) {
            photoRepository
                .search(lastSearch.searchPhrase, lastSearch.currentPageNumber + 1)
                .async(appSchedulers)
                .map { photoDomainDataFactory.create(it, lastSearch) }
                .subscribeBy(
                    onSuccess = {
                        onSuccessSearch(it)
                    },
                    onError = {}
                ).addTo(compositeDisposable)
        }
    }

    private fun onSuccessSearch(photoSearchDomainData: PhotoSearchDomainData) {
        saveSearch(photoSearchDomainData)
        broadcastSearchEvent(photoSearchDomainData)
    }

    private fun saveSearch(photoSearchDomainData: PhotoSearchDomainData) {
        photoRepository.saveSearch(photoSearchDomainData)
    }

    private fun broadcastSearchEvent(photoSearchDomainData: PhotoSearchDomainData) {
        if (photoSearchDomainData.photosDomainData.photos.isEmpty()) {
            photoDomainEvents.search.onNext(
                photoSearchEventFactory.empty()
            )
        } else {
            photoDomainEvents.search.onNext(
                photoSearchEventFactory.ready(photoSearchDomainData)
            )
        }
    }

    private fun onErrorSearch() {
        photoDomainEvents.search.onNext(
            photoSearchEventFactory.error()
        )
    }

}