package com.art.paybackapp.presentation.screens.search.ui.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.art.paybackapp.R

@Composable
fun ErrorContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
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