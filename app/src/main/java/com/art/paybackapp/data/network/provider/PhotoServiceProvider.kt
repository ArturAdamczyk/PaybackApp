package com.art.paybackapp.data.network.provider

import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.data.network.service.PhotoApi
import javax.inject.Inject

class PhotoServiceProvider @Inject constructor(
    private val photoService: RetrofitPhotoService
): PhotoApi {

    override fun search(searchString: String): PhotoSearchDto {
        return photoService
            .search(
                searchString = searchString
            )
            .execute()
            .body() ?: throw PhotoServiceException.ResponseError()
    }

    sealed class PhotoServiceException: Exception(){
        class ResponseError: PhotoServiceException()
    }

}