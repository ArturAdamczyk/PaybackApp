package com.art.paybackapp

import com.art.paybackapp.data.network.model.PhotoDto
import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.domain.model.PhotoDomainData
import com.art.paybackapp.domain.model.PhotoSearchDomainData
import com.art.paybackapp.domain.model.PhotosDomainData

object PhotoDomainTestDataSet {

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