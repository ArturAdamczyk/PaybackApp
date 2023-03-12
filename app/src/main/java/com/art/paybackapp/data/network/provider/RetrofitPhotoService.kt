package com.art.paybackapp.data.network.provider

import com.art.paybackapp.data.network.model.PhotoSearchDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPhotoService {

    @GET("api/")
    fun search(
        @Query("q") searchString: String,
        @Query("image_type") imageType: String = "photo",
        @Query("editors_choice") editorsChoice: Boolean = true,
        @Query("per_page") perPage: Int = 100
    ): Single<PhotoSearchDto>

}