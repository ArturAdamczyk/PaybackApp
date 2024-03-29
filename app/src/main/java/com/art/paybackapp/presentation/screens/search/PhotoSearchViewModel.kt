package com.art.paybackapp.presentation.screens.search

import com.art.paybackapp.common_android.base.BaseViewModel
import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.common.asyncToMain
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoSearchEvent
import com.art.paybackapp.domain.model.PhotoSearchState
import com.art.paybackapp.presentation.screens.search.model.PhotoSearchDisplayable
import com.art.paybackapp.presentation.screens.search.model.PhotoSearchDisplayableFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoSearchViewModel @Inject constructor(
    private val photoDomain: PhotoDomain,
    private val photoDomainEvents: PhotoDomainEvents,
    private val photoSearchDisplayableFactory: PhotoSearchDisplayableFactory,
    private val schedulers: AppSchedulers
) : BaseViewModel() {

    private val state = MutableStateFlow<PhotoSearchScreenState>(PhotoSearchScreenState.Initial)
    fun state(): StateFlow<PhotoSearchScreenState> = state

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun bind() {
        photoDomain.bind()
        observeSearchDomainEvents()
    }

    override fun unbind() {
        photoDomain.unbind()
    }

    fun search(searchPhrase: String = "fruits") {
        if (searchPhrase.isEmpty()) {
            state.value = PhotoSearchScreenState.Empty
        } else {
            state.value = PhotoSearchScreenState.Loading
            photoDomain.search(searchPhrase)
        }
    }

    fun searchMore(){
        photoDomain.searchMore()
    }

    private fun observeSearchDomainEvents() {
        photoDomainEvents
            .search()
            .asyncToMain(schedulers)
            .subscribeBy(
                onNext = {
                    when (it.photoSearchState) {
                        PhotoSearchState.Ready -> {
                            state.value = PhotoSearchScreenState.ShowPhotos(mapSearchData(it))
                        }
                        PhotoSearchState.Empty -> state.value = PhotoSearchScreenState.Empty
                        PhotoSearchState.Error -> state.value = PhotoSearchScreenState.Error
                    }
                },
                onError = {
                    state.value = PhotoSearchScreenState.Error
                }
            ).addTo(compositeDisposable)
    }

    private fun mapSearchData(photoSearchEvent: PhotoSearchEvent): PhotoSearchDisplayable {
        return photoSearchDisplayableFactory.create(photoSearchEvent)
    }

}