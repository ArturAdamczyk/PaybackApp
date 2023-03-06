package com.art.paybackapp.data.network.mapper

import com.art.paybackapp.common.Mapper
import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.domain.model.PhotoSearchDomainData

class PhotoSearchDtoMapper(
    private val photoDtoMapper: PhotoDtoMapper
) : Mapper<PhotoSearchDto, PhotoSearchDomainData>() {

    override fun mapFrom(from: PhotoSearchDto): PhotoSearchDomainData {
        return PhotoSearchDomainData(
            photos = from.hits.map {
                photoDtoMapper.mapFrom(it)
            }
        )
    }

    override fun mapTo(to: PhotoSearchDomainData): PhotoSearchDto {
        TODO("Not yet implemented")
    }

}