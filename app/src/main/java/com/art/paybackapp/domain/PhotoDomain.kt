package com.art.paybackapp.domain

import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.common.Bindable
import com.art.paybackapp.common.asyncToMain
import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.service.PhotoApi
import com.art.paybackapp.domain.model.PhotoSearchDomainData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

class PhotoDomain(
    private val photoApi: PhotoApi,
    private val photoSearchDtoMapper: PhotoSearchDtoMapper,
    private val appSchedulers: AppSchedulers,
    private val photoDomainEvents: PhotoDomainEvents,
    private val photoSearchEventFactory: PhotoSearchEventFactory
): Bindable {

    private var compositeDisposable = CompositeDisposable()

    override fun bind() {
         // no-op
    }

    override fun unbind() {
        compositeDisposable.clear()
    }
    fun search(phrase: String) {
        photoApi
            .search(phrase)
            .asyncToMain(appSchedulers)
            .map { photoSearchDtoMapper.mapFrom(it) }
            .subscribeBy(
                onSuccess = {
                    onSuccessSearch(it)
                },
                onError = {
                    onErrorSearch()
                }
            ).addTo(compositeDisposable)
    }

    private fun onSuccessSearch(photoSearchDomainData: PhotoSearchDomainData) {
        if(photoSearchDomainData.photos.isEmpty()){
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