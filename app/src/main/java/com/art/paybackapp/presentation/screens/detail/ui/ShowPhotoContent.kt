package com.art.paybackapp.presentation.screens.detail.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.art.paybackapp.presentation.screens.detail.model.PhotoDetailDisplayable

@Composable
fun ShowPhotoContent(
    photoDetailDisplayable: PhotoDetailDisplayable
) {

    BoxWithConstraints(
        modifier = Modifier.padding(16.dp)
    ) {
        if (maxWidth < maxHeight) {
            ShowPhotoContentPortrait(photoDetailDisplayable = photoDetailDisplayable)
        } else {
            ShowPhotoContentLandscape(photoDetailDisplayable = photoDetailDisplayable)
        }
    }

}