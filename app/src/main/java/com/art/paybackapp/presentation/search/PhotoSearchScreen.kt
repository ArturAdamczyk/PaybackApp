package com.art.paybackapp.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.art.paybackapp.R
import com.art.paybackapp.presentation.search.ui.LazyPhotoList
import com.art.paybackapp.presentation.search.ui.SearchBar

@Composable
fun PhotoSearchScreen(
    viewModel: PhotoSearchViewModel = hiltViewModel(),
    onNavigate: (Int) -> (Unit)
) {

    val viewState = viewModel.state().collectAsStateWithLifecycle().value
    var searchTextState = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    when(viewState){
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
                onPickedPhoto = { onNavigate(it) },
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

@Composable fun Loading(
    searchTextState: MutableState<TextFieldValue>,
    onSearch: (TextFieldValue) -> Unit = {},
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        Column {
            SearchBar(
                state = searchTextState,
                onSearch = onSearch
            )
            LoadingContent()
        }
    }
}

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowPhotosContent(
    data: PhotoSearchDisplayable,
    onPickedPhoto: (Int) -> Unit = {}
) {
    Column {
        LazyPhotoList(data.photos, onPickedPhoto)
    }
}

@Composable
fun ShowPhotos(
    data: PhotoSearchDisplayable,
    searchTextState: MutableState<TextFieldValue>,
    onPickedPhoto: (Int) -> Unit = {},
    onSearch: (TextFieldValue) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp,
            ),
    ) {
        Column {
            SearchBar(
                state = searchTextState,
                onSearch = onSearch
            )
            ShowPhotosContent(
                data = data,
                onPickedPhoto = onPickedPhoto
            )
        }
    }
}

@Composable
fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Text(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            ),
            text = stringResource(R.string.emptyHint),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
            )
        )
    }
}

@Composable
fun Empty(
    searchTextState: MutableState<TextFieldValue>,
    onSearch: (TextFieldValue) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        Column {
            SearchBar(
                state = searchTextState,
                onSearch = onSearch
            )
            EmptyContent()
        }
    }
}

@Composable
fun ErrorContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            ),
            text = stringResource(R.string.errorHint),
            style = TextStyle(
                fontSize = 20.sp,
            )
        )
    }
}

@Composable
fun Error(
    searchTextState: MutableState<TextFieldValue>,
    onSearch: (TextFieldValue) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Column {
            SearchBar(
                state = searchTextState,
                onSearch = onSearch
            )
            ErrorContent()
        }
    }
}