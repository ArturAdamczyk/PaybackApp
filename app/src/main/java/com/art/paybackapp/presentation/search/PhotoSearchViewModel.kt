package com.art.paybackapp.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.common.Bindable
import com.art.paybackapp.common.asyncToMain
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoSearchEvent
import com.art.paybackapp.domain.model.PhotoSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class PhotoSearchViewModel @Inject constructor(
    private val photoDomain: PhotoDomain,
    private val photoDomainEvents: PhotoDomainEvents,
    private val photoSearchDisplayableFactory: PhotoSearchDisplayableFactory,
    private val schedulers: AppSchedulers
) : ViewModel(), Bindable {

    private val _uiState = mutableStateOf<PhotoSearchScreenState>(PhotoSearchScreenState.Initial)
    val uiState: State<PhotoSearchScreenState>
        get() = _uiState

    var compositeDisposable : CompositeDisposable =  CompositeDisposable()

    override fun bind() {
        photoDomain.bind()
        listenOnSearch()
    }

    override fun unbind() {
        photoDomain.unbind()
    }

    fun search(searchPhrase: String) {
        _uiState.value = PhotoSearchScreenState.Loading
        photoDomain.search(searchPhrase)
    }

    private fun listenOnSearch() {
        photoDomainEvents
            .search()
            .asyncToMain(schedulers)
            .subscribeBy(
                onNext = {
                    when(it.photoSearchState){
                        PhotoSearchState.Ready -> {
                            _uiState.value = PhotoSearchScreenState.ShowPhotos(mapSearchData(it))
                        }
                        PhotoSearchState.Empty -> _uiState.value = PhotoSearchScreenState.Empty
                        PhotoSearchState.Error -> _uiState.value = PhotoSearchScreenState.Error
                    }
                },
                onError = {
                    _uiState.value = PhotoSearchScreenState.Error
                }
            ).addTo(compositeDisposable)
    }

    private fun mapSearchData(photoSearchEvent: PhotoSearchEvent): PhotoSearchDisplayable{
        return photoSearchDisplayableFactory.create(photoSearchEvent)
    }

}