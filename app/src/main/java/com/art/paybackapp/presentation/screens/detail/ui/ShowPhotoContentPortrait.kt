package com.art.paybackapp.presentation.screens.detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.art.paybackapp.presentation.screens.detail.PhotoDetailDisplayable
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ShowPhotoContentPortrait(
    photoDetailDisplayable: PhotoDetailDisplayable
) {
    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        GlideImage(
            imageModel = {
                photoDetailDisplayable.photo.largeImageUrl
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .weight(0.6f)
                .padding(16.dp)
                .clip((RectangleShape))
                .fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .weight(0.4f)
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = photoDetailDisplayable.photo.username,
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = photoDetailDisplayable.photo.tags
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = photoDetailDisplayable.photo.likes
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = photoDetailDisplayable.photo.downloads
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = photoDetailDisplayable.photo.comments
            )
        }
    }
}