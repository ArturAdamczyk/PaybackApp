package com.art.paybackapp.presentation.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.art.paybackapp.presentation.search.PhotoDisplayable

@Composable
fun PhotoItem(
    photo: PhotoDisplayable, onPicked: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    onPicked(photo.id)
                }
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = RoundedCornerShape(corner = CornerSize(8.dp))

    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f), Arrangement.Center,
            ) {
                Text(
                    text = "Username: " + photo.id.toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Text(
                    text = "Likes: " + photo.id.toString(), style = TextStyle(
                        color = Color.Black, fontSize = 15.sp
                    )
                )
                Text(
                    text = "Downloads: " + photo.id.toString(), style = TextStyle(
                        color = Color.Black, fontSize = 15.sp
                    )
                )
                Text(
                    text = "Comments: " + photo.id.toString(), style = TextStyle(
                        color = Color.Black, fontSize = 15.sp
                    )
                )
                Text(
                    text = "Tags: " + photo.id.toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
            }

/*            Image(
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp)
                    .clip((RectangleShape))
            )*/

        }
    }
}