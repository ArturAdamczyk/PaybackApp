package com.art.paybackapp.presentation.screens.search.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import com.art.paybackapp.presentation.screens.search.PhotoDisplayable

@Composable
fun LazyPhotoList(
    photos: List<PhotoDisplayable>,
    onPicked: (Int) -> Unit = {},
    onLoadMore: () -> Unit = {}
) {

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(photos) { item ->
            PhotoItem(item, onPicked)
        }
    }

    listState.OnBottomReached(
        loadMoreOffset = (photos.size * 0.5).toInt(),
        loadMore = {
            onLoadMore.invoke()
        }
    )
}

@Composable
fun LazyListState.OnBottomReached(
    loadMoreOffset: Int,
    loadMore : () -> Unit
) {

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo
                .lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - loadMoreOffset
        }
    }

    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMore()
            }
    }
}