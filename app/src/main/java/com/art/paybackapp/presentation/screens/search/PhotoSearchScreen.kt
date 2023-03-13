package com.art.paybackapp.presentation.screens.search

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.art.paybackapp.presentation.screens.search.ui.loading.Loading
import com.art.paybackapp.presentation.screens.search.ui.empty.Empty
import com.art.paybackapp.presentation.screens.search.ui.error.Error
import com.art.paybackapp.presentation.screens.search.ui.photos.ShowPhotos

@Composable
fun PhotoSearchScreen(
    viewModel: PhotoSearchViewModel,
    onNavigate: (Int) -> (Unit)
) {

    val viewState = viewModel.state().collectAsStateWithLifecycle().value
    var searchTextState =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    when (viewState) {
        is PhotoSearchScreenState.Initial -> {
            viewModel.search()
        }
        is PhotoSearchScreenState.Loading -> {
            Loading(
                searchTextState = searchTextState,
                onSearch = {
                    viewModel.search(it.text)
                }
            )
        }
        is PhotoSearchScreenState.ShowPhotos -> {
            ShowPhotos(
                data = viewState.photoSearchDisplayable,
                searchTextState = searchTextState,
                onPickedPhoto = {
                    onNavigate(it)
                },
                onSearch = {
                    viewModel.search(it.text)
                }
            )
        }
        is PhotoSearchScreenState.Empty -> {
            Empty(
                searchTextState = searchTextState,
                onSearch = {
                    viewModel.search(it.text)
                }
            )
        }
        is PhotoSearchScreenState.Error -> {
            Error(
                searchTextState = searchTextState,
                onSearch = {
                    viewModel.search(it.text)
                }
            )
        }
    }

}













