package com.art.paybackapp.data.network.model

import com.google.gson.annotations.SerializedName

data class PhotoSearchDto(

    @SerializedName("total")
    val total: Int,

    @SerializedName("totalHits")
    val totalHits: Int,

    @SerializedName("hits")
    val hits: List<PhotoDto>

)