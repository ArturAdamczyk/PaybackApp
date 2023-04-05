package com.art.paybackapp.presentation.screens.search.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.art.paybackapp.R
import com.art.paybackapp.presentation.screens.search.model.PhotoDisplayable
import com.art.paybackapp.presentation.ui.composables.DialogPrompt
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PhotoItem(
    photo: PhotoDisplayable,
    onPicked: (Int) -> Unit = {}
) {

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        DialogPrompt(
            title = stringResource(id = R.string.dialogTitle),
            confirmText = stringResource(id = R.string.yes),
            dismissText = stringResource(id = R.string.no),
            onConfirm = {
                openDialog.value = false
                onPicked(photo.id)
            },
            onDismiss = {
                openDialog.value = false
            }
        )
    }

    Card(
        modifier = Modifier
            .padding(
                horizontal = 12.dp,
                vertical = 12.dp
            )
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    openDialog.value = true
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = { photo.imagePreviewUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                    .size(60.dp)
                    .clip((CircleShape))
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        bottom = 4.dp,
                    ),
                    text = photo.username,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = photo.tags,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
            }
        }
    }
}