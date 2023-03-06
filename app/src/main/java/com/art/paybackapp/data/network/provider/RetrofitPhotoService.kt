package com.art.paybackapp.data.network.provider

import com.art.paybackapp.data.network.model.PhotoSearchDto
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPhotoService {

    @GET("/")
    fun search(@Query("q") searchString: String): Single<PhotoSearchDto>

}