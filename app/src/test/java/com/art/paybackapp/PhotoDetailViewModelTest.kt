package com.art.paybackapp

import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.*
import com.art.paybackapp.presentation.screens.detail.model.PhotoDetailDisplayable
import com.art.paybackapp.presentation.screens.detail.model.PhotoDetailDisplayableFactory
import com.art.paybackapp.presentation.screens.detail.PhotoDetailScreenState
import com.art.paybackapp.presentation.screens.detail.PhotoDetailViewModel
import com.art.paybackapp.presentation.screens.search.model.PhotoDisplayable
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
                photosDomainData = PhotosDomainData(listOf(
                        PhotoDomainData(
                            id = 1,
                            tags = "",
                            previewUrl = "",
                            largeImageUrl = "",
                            downloads = 3,
                            likes = 2,
                            comments = 0,
                            user = ""
                        )
                    )
                )
            )
        )
        val photoId = 1

        every { photoDomainEvents.lastSearch() } returns searchEvent
        every { photoDetailDisplayableFactory.create(any()) } returns PhotoDetailDisplayable(
            PhotoDisplayable(
                id = 1,
                tags = "",
                imagePreviewUrl = "",
                largeImageUrl = "",
                downloads = "3",
                likes = "2",
                comments = "0",
                username = ""
            )
        )

        // When:

        serviceUnderTest.loadPhoto(photoId)

        // Then:

        verify {
            photoDomainEvents.lastSearch()
        }

        Assertions.assertTrue(serviceUnderTest.state().value is PhotoDetailScreenState.ShowPhoto)

    }

    @Test
    fun `GIVEN no photo WHEN loadPhoto is invoked THEN screen state should be set to NoPhoto`() {

        // Given

        val searchEvent = null
        val photoId = 1

        every { photoDomainEvents.lastSearch() } returns searchEvent

        // When:

        serviceUnderTest.loadPhoto(photoId)

        // Then:

        verify {
            photoDomainEvents.lastSearch()
        }

        Assertions.assertTrue(serviceUnderTest.state().value is PhotoDetailScreenState.NoPhoto)

    }

}