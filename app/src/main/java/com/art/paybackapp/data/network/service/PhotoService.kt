package com.art.paybackapp.data.network.service

import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.data.network.provider.PhotoServiceProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PhotoService @Inject constructor(
    private val photoServiceProvider: PhotoServiceProvider,
) : PhotoApi {

    override fun search(searchString: String): Single<PhotoSearchDto> {
        return photoServiceProvider.search(searchString = searchString)
    }

}