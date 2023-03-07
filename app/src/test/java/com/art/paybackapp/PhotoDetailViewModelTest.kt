package com.art.paybackapp

import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoDomainData
import com.art.paybackapp.domain.model.PhotoSearchDomainData
import com.art.paybackapp.domain.model.PhotoSearchEvent
import com.art.paybackapp.domain.model.PhotoSearchState
import com.art.paybackapp.presentation.detail.PhotoDetailDisplayable
import com.art.paybackapp.presentation.detail.PhotoDetailDisplayableFactory
import com.art.paybackapp.presentation.detail.PhotoDetailScreenState
import com.art.paybackapp.presentation.detail.PhotoDetailViewModel
import com.art.paybackapp.presentation.search.PhotoDisplayable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PhotoDetailViewModelTest {

    private val photoDomainEvents: PhotoDomainEvents by lazy { mockk<PhotoDomainEvents>() }

    private val photoDetailDisplayableFactory: PhotoDetailDisplayableFactory by lazy { mockk<PhotoDetailDisplayableFactory>() }

    @MockK
    lateinit var serviceUnderTest: PhotoDetailViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        serviceUnderTest = spyk(
            PhotoDetailViewModel(
                photoDomainEvents,
                photoDetailDisplayableFactory,
            ),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `GIVEN photo WHEN loadPhoto is invoked THEN screen state should be set to ShowPhoto`() {

        // Given

        val searchEvent = PhotoSearchEvent(
            PhotoSearchState.Ready,
            PhotoSearchDomainData(
                photos = listOf(
                    PhotoDomainData(
                        id = 1,
                        tags = "",
                        previewUrl = "",
                        largeImageURl = "",
                        fullHDURL = "",
                        imageURL = "",
                        downloads = 3,
                        likes = 2,
                        comments = 0,
                        user = ""
                    )
                )
            )
        )
        val photoId = 1

        every { photoDomainEvents.lastSearch() } returns searchEvent
        every { photoDetailDisplayableFactory.create(any()) } returns PhotoDetailDisplayable(PhotoDisplayable(1))

        // When:

        serviceUnderTest.loadPhoto(photoId)

        // Then:

        verify {
            photoDomainEvents.lastSearch()
        }

        Assertions.assertTrue(serviceUnderTest.uiState.value is PhotoDetailScreenState.ShowPhoto)

    }

}