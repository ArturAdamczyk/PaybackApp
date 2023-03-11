package com.art.paybackapp.presentation.search

import com.art.paybackapp.base.BaseViewModel
import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.common.asyncToMain
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoSearchEvent
import com.art.paybackapp.domain.model.PhotoSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit
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

    private var searchQuery: PublishSubject<String> = PublishSubject.create()
    private fun searchQuery(): Observable<String> = searchQuery

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val queryDebounceTimeout = 600L

    override fun bind() {
        photoDomain.bind()
        observeSearchDomainEvents()
        observeSearchQuery(
            action = {
                state.value = PhotoSearchScreenState.Loading
                photoDomain.search(it)
            }
        )
    }

    override fun unbind() {
        photoDomain.unbind()
    }

    fun search(searchPhrase: String = "fruits") {
        if (searchPhrase.isEmpty()) {
            state.value = PhotoSearchScreenState.Empty
        } else {
            searchQuery.onNext(searchPhrase)
        }
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

    private fun observeSearchQuery(action: (String) -> Unit) {
        searchQuery()
            .debounce(queryDebounceTimeout, TimeUnit.MILLISECONDS)
            .map { text -> text.lowercase() }
            .observeOn(schedulers.main())
            .subscribe { query ->
                action.invoke(query)
            }
            .addTo(compositeDisposable)
    }

}