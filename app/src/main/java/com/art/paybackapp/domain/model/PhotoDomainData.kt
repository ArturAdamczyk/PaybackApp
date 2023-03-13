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
) : Cloneable {
    public override fun clone(): PhotoDomainData {
        return PhotoDomainData(
            id = id,
            tags = tags,
            previewUrl = previewUrl,
            largeImageUrl = largeImageUrl,
            downloads = downloads,
            likes = likes,
            comments = comments,
            user = user
        )
    }
}