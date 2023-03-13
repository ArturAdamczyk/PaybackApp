package com.art.paybackapp

import com.art.paybackapp.data.network.model.PhotoDto
import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.domain.model.PhotoDomainData
import com.art.paybackapp.domain.model.PhotoSearchDomainData
import com.art.paybackapp.domain.model.PhotosDomainData

object PhotoDomainTestDataSet {

    val photoSearchDto_zebra = PhotoSearchDto(
        total = 1,
        totalHits = 1,
        listOf(
            PhotoDto(
                id = 1,
                pageUrl = "",
                type = "",
                tags = "zebra, animal",
                previewUrl = "www.blablaxyz.com",
                previewWidth = 54,
                previewHeight = 96,
                webformatUrl = "",
                webformatWidth = 136,
                webformatHeight = 214,
                largeImageURl = "",
                fullHDURL = "www.blablaxyz_hd.com",
                imageURL = "www.blablaxyz_normal.com",
                imageWidth = 468,
                imageHeight = 512,
                imageSize = 1,
                views = 23,
                downloads = 3,
                likes = 1,
                comments = 2,
                user_id = 1,
                user = "someone",
                userImageURL = "wwww.blabla.com"
            )
        )
    )

    val photoSearchDomainData_zebra = PhotoSearchDomainData(
        photosDomainData = PhotosDomainData(listOf(
                PhotoDomainData(
                    id = 1,
                    tags = "zebra, animal",
                    previewUrl = "www.blablaxyz.com",
                    largeImageUrl = "www.blablaxyz_hd.com",
                    downloads = 3,
                    likes = 1,
                    comments = 2,
                    user = "someone"
                )
            )
        ),
        searchPhrase = "",
        currentPageNumber = 1

    )

    val photoSearchDomainData_zebraMore = PhotoSearchDomainData(
        photosDomainData = PhotosDomainData(listOf(
            PhotoDomainData(
                id = 1,
                tags = "zebra, animal",
                previewUrl = "www.blablaxyz.com",
                largeImageUrl = "www.blablaxyz_hd.com",
                downloads = 3,
                likes = 1,
                comments = 2,
                user = "someone"
            ),
            PhotoDomainData(
                id = 2,
                tags = "zebra, animal, yellow",
                previewUrl = "www.blablaxyz.com",
                largeImageUrl = "www.blablaxyz_hd.com",
                downloads = 67,
                likes = 3,
                comments = 6,
                user = "other someone"
            )
        )
        ),
        searchPhrase = "",
        currentPageNumber = 2

    )

    val photosDomainData_zebra = PhotosDomainData(listOf(
            PhotoDomainData(
                id = 1,
                tags = "zebra, animal",
                previewUrl = "www.blablaxyz.com",
                largeImageUrl = "www.blablaxyz_hd.com",
                downloads = 3,
                likes = 1,
                comments = 2,
                user = "someone"
            )
        )
    )

}