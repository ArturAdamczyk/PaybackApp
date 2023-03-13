package com.art.paybackapp.data.network.provider

import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.data.network.service.PhotoApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PhotoServiceProvider @Inject constructor(
    private val photoService: RetrofitPhotoService
) : PhotoApi {

    override fun search(searchString: String, pageNumber: Int): Single<PhotoSearchDto> {
        return photoService.search(
            searchString = searchString,
            pageNumber = pageNumber
        )
    }

}