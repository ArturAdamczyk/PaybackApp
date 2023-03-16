package com.art.paybackapp

import com.art.paybackapp.common.TestSchedulers
import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.data.repository.PhotoRepository
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.PhotoSearchEventFactory
import com.art.paybackapp.domain.model.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class PhotoDomainTest {

    private val photoDomainEvents: PhotoDomainEvents by lazy { mockk<PhotoDomainEvents>() }

    private val photoSearchEventFactory: PhotoSearchEventFactory by lazy { mockk<PhotoSearchEventFactory>() }

    private val photoRepository: PhotoRepository by lazy { mockk<PhotoRepository>() }

    private val photoSearchDomainDataFactory: PhotoSearchDomainDataFactory by lazy { mockk<PhotoSearchDomainDataFactory>() }

    private val schedulers: TestSchedulers = TestSchedulers()

    @MockK
    lateinit var serviceUnderTest: PhotoDomain
    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        serviceUnderTest = spyk(
            PhotoDomain(
                photoRepository,
                schedulers,
                photoDomainEvents,
                photoSearchEventFactory,
                photoSearchDomainDataFactory
            ),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `GIVEN search data WHEN search is invoked THEN ready event should be broadcast`() {

        // Given

        val searchPhrase = "zebra"
        val photoSearchState = PhotoSearchState.Ready
        val photoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebra
        val photosDomainData =  PhotoDomainTestDataSet.photosDomainData_zebra
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, photoSearchDomainData)

        val slots = mutableListOf<PhotoSearchEvent>()
        every { photoDomainEvents.search.onNext(capture(slots)) } just Runs
        every { photoRepository.search(any()) } answers { Single.just(photosDomainData) }
        every { photoSearchDomainDataFactory.create(any(), any<String>()) } returns photoSearchDomainData
        every { photoSearchEventFactory.ready(any()) } returns photoSearchEvent
        every { photoRepository.saveSearch(any()) } just Runs

        // When:

        serviceUnderTest.search(searchPhrase)
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoRepository.search(any())
            photoRepository.saveSearch(any())
            photoSearchEventFactory.ready(any())
            photoDomainEvents.search.onNext(capture(slots))
        }

        assertEquals(slots[0].photoSearchState, photoSearchState)
        assertEquals(slots[0].photoSearchDomainData, photoSearchDomainData)

    }

    @Test
    fun `GIVEN previous search data WHEN searchMore is invoked THEN ready event should be broadcast`() {

        // Given

        val searchPhrase = "zebra"
        val photoSearchState = PhotoSearchState.Ready
        val previousPhotoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebra
        val photoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebraMore
        val photosDomainData =  PhotoDomainTestDataSet.photosDomainData_zebra
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, photoSearchDomainData)


        val slots = mutableListOf<PhotoSearchEvent>()
        every { photoDomainEvents.search.onNext(capture(slots)) } just Runs
        every { photoRepository.search(any(), any()) } answers { Single.just(photosDomainData) }
        every { photoSearchDomainDataFactory.create(any(), any<PhotoSearchDomainData>()) } returns photoSearchDomainData
        every { photoSearchEventFactory.ready(any()) } returns photoSearchEvent
        every { photoRepository.saveSearch(any()) } just Runs
        every { photoRepository.lastSearch() } returns previousPhotoSearchDomainData

        // When:

        serviceUnderTest.searchMore()
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoRepository.search(any(), any())
            photoSearchDomainDataFactory.create(any(), any<PhotoSearchDomainData>())
            photoRepository.saveSearch(any())
            photoSearchEventFactory.ready(any())
            photoDomainEvents.search.onNext(capture(slots))
        }

        assertEquals(slots[0].photoSearchState, photoSearchState)
        assertEquals(slots[0].photoSearchDomainData, photoSearchDomainData)

    }

    @Test
    fun `GIVEN no previous search data WHEN searchMore is invoked THEN nothing should happen`() {

        // Given

        val searchPhrase = "zebra"
        val photoSearchState = PhotoSearchState.Ready
        val previousPhotoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebra
        val photoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebraMore
        val photosDomainData =  PhotoDomainTestDataSet.photosDomainData_zebra
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, photoSearchDomainData)

        every { photoRepository.search(any(), any()) } answers { Single.just(photosDomainData) }
        every { photoSearchDomainDataFactory.create(any(), any<PhotoSearchDomainData>()) } returns previousPhotoSearchDomainData
        every { photoSearchEventFactory.ready(any()) } returns photoSearchEvent
        every { photoRepository.saveSearch(any()) } just Runs
        every { photoRepository.lastSearch() } returns null

        // When:

        serviceUnderTest.searchMore()
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoDomainEvents.search wasNot Called
            photoRepository.search(any()) wasNot Called
        }

    }

    @Test
    fun `GIVEN search data is empty WHEN search is invoked THEN empty event should be broadcast`() {

        // Given

        val searchPhrase = "zebra"
        val photoSearchState = PhotoSearchState.Empty
        val photoSearchDto = PhotoSearchDto(
            total = 0,
            totalHits = 0,
            hits = listOf()
        )
        val photosDomainData = PhotosDomainData()
        val photoSearchDomainData = PhotoSearchDomainData(
            photosDomainData = photosDomainData,
            searchPhrase = searchPhrase,
            currentPageNumber = 1
        )
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, photoSearchDomainData)


        val slots = mutableListOf<PhotoSearchEvent>()
        every { photoDomainEvents.search.onNext(capture(slots)) } just Runs
        every { photoRepository.search(any(), any()) } answers { Single.just(photosDomainData) }
        every { photoSearchDomainDataFactory.create(any(), any<String>()) } returns photoSearchDomainData
        every { photoSearchEventFactory.empty() } returns photoSearchEvent
        every { photoRepository.saveSearch(any()) } just Runs

        // When:

        serviceUnderTest.search(searchPhrase)
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoRepository.search(any())
            photoRepository.saveSearch(any())
            photoSearchEventFactory.empty()
            photoDomainEvents.search.onNext(capture(slots))
        }

        assertEquals(slots[0].photoSearchState, photoSearchState)
        assertEquals(slots[0].photoSearchDomainData, photoSearchDomainData)

    }

    @Test
    fun `GIVEN api search error WHEN search is invoked THEN error event should be broadcast`() {

        // Given

        val searchPhrase = "zebra"
        val photoSearchState = PhotoSearchState.Error
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, PhotoSearchDomainData())


        val slots = mutableListOf<PhotoSearchEvent>()
        every { photoDomainEvents.search.onNext(capture(slots)) } just Runs
        every { photoRepository.search(any(), any()) } answers { Single.error(Exception()) }
        every { photoSearchEventFactory.error() } returns photoSearchEvent
        every { photoRepository.saveSearch(any()) } just Runs

        // When:

        serviceUnderTest.search(searchPhrase)
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoRepository.search(any())
            photoSearchEventFactory.error()
            photoDomainEvents.search.onNext(capture(slots))
        }

        assertEquals(slots[0].photoSearchState, photoSearchState)

    }
}