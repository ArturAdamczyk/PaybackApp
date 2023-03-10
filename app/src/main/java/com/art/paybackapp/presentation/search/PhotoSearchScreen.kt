package com.art.paybackapp.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.art.paybackapp.presentation.search.ui.LazyPhotoList
import com.art.paybackapp.presentation.search.ui.SearchBar

@Composable
fun PhotoSearchScreen(
    viewModel: PhotoSearchViewModel = hiltViewModel(),
    onNavigate: (Int) -> (Unit)
) {

    when(val viewState = viewModel.state().collectAsStateWithLifecycle().value){
            is PhotoSearchScreenState.Initial -> {
                viewModel.search()
            }
            is PhotoSearchScreenState.Loading -> {
                LoadingContent()
            }
            is PhotoSearchScreenState.ShowPhotos -> {
                ShowPhotosContent(
                    data = viewState.photoSearchDisplayable,
                    onPickedPhoto = { onNavigate(it) }
                )
            }
            is PhotoSearchScreenState.Empty -> {
                EmptyContent()
            }
            is PhotoSearchScreenState.Error -> {
                ErrorContent()
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
    SearchBar()
    LazyPhotoList(data.photos, onPickedPhoto)
}

@Composable
fun EmptyContent() {
    Text(
        text = "No photos found for given criteria :(",
        style = TextStyle(
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}

@Composable
fun ErrorContent() {
    Text(
        text = "Something went wrong :(",
        style = TextStyle(
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}