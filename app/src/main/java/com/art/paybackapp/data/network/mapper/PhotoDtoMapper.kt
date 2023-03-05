package com.art.paybackapp.data.network.mapper

import com.art.paybackapp.common.Mapper
import com.art.paybackapp.data.network.model.PhotoDto
import com.art.paybackapp.domain.model.PhotoDomainData

class PhotoDtoMapper : Mapper<PhotoDto, PhotoDomainData>() {

    override fun mapFrom(from: PhotoDto): PhotoDomainData {
        TODO("Not yet implemented")
    }

    override fun mapTo(to: PhotoDomainData): PhotoDto {
        TODO("Not yet implemented")
    }

}