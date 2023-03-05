package com.art.paybackapp.data.network.service

import com.art.paybackapp.data.network.model.PhotoSearchDto

interface PhotoApi {

    fun search(searchString: String): PhotoSearchDto

}