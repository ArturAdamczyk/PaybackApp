package com.art.paybackapp.data.network.mapper

import com.art.paybackapp.common.Mapper
import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.domain.model.PhotosDomainData

class PhotoSearchDtoMapper(
    private val photoDtoMapper: PhotoDtoMapper
) : Mapper<PhotoSearchDto, PhotosDomainData>() {

    override fun mapFrom(from: PhotoSearchDto): PhotosDomainData {
        return PhotosDomainData(
            photos = from.hits.map {
                photoDtoMapper.mapFrom(it)
            }
        )
    }

    override fun mapTo(to: PhotosDomainData): PhotoSearchDto {
        TODO("Not yet implemented")
    }

}