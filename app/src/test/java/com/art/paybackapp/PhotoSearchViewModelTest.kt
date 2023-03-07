package com.art.paybackapp

import com.art.paybackapp.common.TestSchedulers
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.model.PhotoSearchDomainData
import com.art.paybackapp.domain.model.PhotoSearchEvent
import com.art.paybackapp.domain.model.PhotoSearchState
import com.art.paybackapp.presentation.search.PhotoSearchDisplayable
import com.art.paybackapp.presentation.search.PhotoSearchDisplayableFactory
import com.art.paybackapp.presentation.search.PhotoSearchScreenState
import com.art.paybackapp.presentation.search.PhotoSearchViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PhotoSearchViewModelTest {

    private val photoDomain: PhotoDomain by lazy { mockk<PhotoDomain>() }

    private val photoDomainEvents: PhotoDomainEvents by lazy { mockk<PhotoDomainEvents>() }

    private val photoSearchDisplayableFactory: PhotoSearchDisplayableFactory by lazy { mockk<PhotoSearchDisplayableFactory>() }

    private val schedulers: TestSchedulers = TestSchedulers()

    @MockK
    lateinit var serviceUnderTest: PhotoSearchViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        serviceUnderTest = spyk(
            PhotoSearchViewModel(
                photoDomain,
                photoDomainEvents,
                photoSearchDisplayableFactory,
                schedulers
            ),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `GIVEN search phrase WHEN search is invoked THEN screen state should be set to Loading`() {

        // Given

        val searchPhrase = "zebra"
        val uiState = PhotoSearchScreenState.Loading
        every { photoDomain.search(any()) } just Runs

        // When:

        serviceUnderTest.search(searchPhrase)
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoDomain.search(any())
        }

        Assertions.assertEquals(serviceUnderTest.uiState.value, uiState)

    }

    @Test
    fun `GIVEN search result is ready WHEN bind is invoked THEN screen state should be set to ShowPhotos`() {

        // Given

        val searchEvent = PhotoSearchEvent(
            PhotoSearchState.Ready,
            PhotoSearchDomainData()
        )
        val photoSearchDisplayable = PhotoSearchDisplayable(listOf())

        every { photoDomain.bind() } just Runs
        every { photoDomainEvents.search() } returns Observable.just(searchEvent)
        every { photoSearchDisplayableFactory.create(any()) } returns photoSearchDisplayable

        // When:

        serviceUnderTest.bind()
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoDomain.bind()
            photoDomainEvents.search()
            photoSearchDisplayableFactory.create(any())
        }

        Assertions.assertTrue(serviceUnderTest.uiState.value is PhotoSearchScreenState.ShowPhotos)
    }

    @Test
    fun `GIVEN search result is empty WHEN bind is invoked THEN screen state should be set to Empty`() {

        // Given

        val searchEvent = PhotoSearchEvent(
            PhotoSearchState.Empty,
            PhotoSearchDomainData()
        )

        every { photoDomain.bind() } just Runs
        every { photoDomainEvents.search() } returns Observable.just(searchEvent)

        // When:

        serviceUnderTest.bind()
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoDomain.bind()
            photoDomainEvents.search()
        }

        Assertions.assertTrue(serviceUnderTest.uiState.value is PhotoSearchScreenState.Empty)
    }

    @Test
    fun `GIVEN search result is error WHEN bind is invoked THEN screen state should be set to Error`() {

        // Given

        val searchEvent = PhotoSearchEvent(
            PhotoSearchState.Error,
            PhotoSearchDomainData()
        )

        every { photoDomain.bind() } just Runs
        every { photoDomainEvents.search() } returns Observable.just(searchEvent)

        // When:

        serviceUnderTest.bind()
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoDomain.bind()
            photoDomainEvents.search()
        }

        Assertions.assertTrue(serviceUnderTest.uiState.value is PhotoSearchScreenState.Error)
    }

}