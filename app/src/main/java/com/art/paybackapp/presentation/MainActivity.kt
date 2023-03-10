package com.art.paybackapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.art.paybackapp.presentation.detail.PhotoDetailViewModel
import com.art.paybackapp.presentation.search.PhotoSearchViewModel
import com.art.paybackapp.presentation.ui.PaybackAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val photoSearchViewModel: PhotoSearchViewModel by viewModels()
    private val photoDetailViewModel: PhotoDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaybackAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(photoSearchViewModel, photoDetailViewModel)
                }
            }
        }
    }

    override fun onResume() {
        photoSearchViewModel.bind()
        photoDetailViewModel.bind()
        super.onResume()
    }

    override fun onStop() {
        photoSearchViewModel.unbind()
        photoDetailViewModel.unbind()
        super.onStop()
    }

}