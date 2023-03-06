package com.art.paybackapp.data.network.service

import com.art.paybackapp.data.network.model.PhotoSearchDto
import io.reactivex.rxjava3.core.Single

interface PhotoApi {
    fun search(searchString: String): Single<PhotoSearchDto>

}