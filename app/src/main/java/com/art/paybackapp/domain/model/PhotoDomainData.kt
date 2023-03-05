package com.art.paybackapp.domain.model

data class PhotoDomainData(
    val id: Int,

    val tags: String,

    val previewUrl: String,

    val largeImageURl: String,

    val fullHDURL: String,

    val imageURL: String,

    val downloads: Int,

    val likes: Int,

    val comments: Int,

    val user: String

)