package com.art.paybackapp.domain.model

data class PhotoDomainData(

    val id: Int,

    val tags: String,

    val previewUrl: String,

    val largeImageUrl: String,

    val downloads: Int,

    val likes: Int,

    val comments: Int,

    val user: String

)