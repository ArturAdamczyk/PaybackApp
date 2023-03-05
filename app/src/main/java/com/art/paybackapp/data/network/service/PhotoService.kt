package com.art.paybackapp.data.network.service

import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.data.network.provider.PhotoServiceProvider
import javax.inject.Inject

class PhotoService @Inject constructor(
    private val photoServiceProvider: PhotoServiceProvider,
    private val photoSearchDtoMapper: PhotoSearchDtoMapper
): PhotoApi {

    override fun search(searchString: String): PhotoSearchDto {
       return photoServiceProvider
            .search(searchString)
            .apply{
                photoSearchDtoMapper.mapFrom(this)
            }
    }

}