package com.art.paybackapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.art.paybackapp.base.BaseActivity
import com.art.paybackapp.base.BaseViewModel
import com.art.paybackapp.presentation.screens.detail.PhotoDetailViewModel
import com.art.paybackapp.presentation.screens.search.PhotoSearchViewModel
import com.art.paybackapp.presentation.ui.styles.PaybackAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val photoSearchViewModel: PhotoSearchViewModel by viewModels()
    private val photoDetailViewModel: PhotoDetailViewModel by viewModels()
    override fun initializeViewModels(): HashMap<String, BaseViewModel> {
        return hashMapOf(
            PhotoSearchViewModel.name to photoSearchViewModel,
            PhotoDetailViewModel.name to photoDetailViewModel
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaybackAppTheme {
                Surface(
                    modifier = Modifier
                        .systemBarsPadding()
                        .fillMaxSize()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        photoSearchViewModel,
                        photoDetailViewModel
                    )
                }
            }
        }
    }

}