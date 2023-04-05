package com.art.paybackapp.presentation.screens.search.model

data class PhotoDisplayable(
    val id: Int,
    val username: String,
    val comments: String,
    val tags: String,
    val likes: String,
    val downloads: String,
    val imagePreviewUrl: String,
    val largeImageUrl: String
)