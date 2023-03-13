package com.art.paybackapp

import com.art.paybackapp.common.TestSchedulers
import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.model.PhotoSearchDto
import com.art.paybackapp.data.network.service.PhotoApi
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

    private val photoApi: PhotoApi by lazy { mockk<PhotoApi>() }

    private val photoDomainEvents: PhotoDomainEvents by lazy { mockk<PhotoDomainEvents>() }

    private val photoSearchDtoMapper: PhotoSearchDtoMapper by lazy { mockk<PhotoSearchDtoMapper>() }

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
                photoApi,
                photoSearchDtoMapper,
                schedulers,
                photoDomainEvents,
                photoSearchEventFactory,
                photoRepository,
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
        val photoSearchDto = PhotoDomainTestDataSet.photoSearchDto_zebra
        val photoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebra
        val photosDomainData =  PhotoDomainTestDataSet.photosDomainData_zebra
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, photoSearchDomainData)


        val slots = mutableListOf<PhotoSearchEvent>()
        every { photoDomainEvents.search.onNext(capture(slots)) } just Runs
        every { photoApi.search(any()) } answers { Single.just(photoSearchDto) }
        every { photoSearchDtoMapper.mapFrom(any()) } returns photosDomainData
        every { photoSearchDomainDataFactory.create(any(), any<String>()) } returns photoSearchDomainData
        every { photoSearchEventFactory.ready(any()) } returns photoSearchEvent
        every { photoRepository.saveLast(any()) } just Runs

        // When:

        serviceUnderTest.search(searchPhrase)
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoApi.search(any())
            photoSearchDtoMapper.mapFrom(any())
            photoRepository.saveLast(any())
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
        val photoSearchDto = PhotoDomainTestDataSet.photoSearchDto_zebra
        val previousPhotoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebra
        val photoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebraMore
        val photosDomainData =  PhotoDomainTestDataSet.photosDomainData_zebra
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, photoSearchDomainData)


        val slots = mutableListOf<PhotoSearchEvent>()
        every { photoDomainEvents.search.onNext(capture(slots)) } just Runs
        every { photoApi.search(any(), any()) } answers { Single.just(photoSearchDto) }
        every { photoSearchDtoMapper.mapFrom(any()) } returns photosDomainData
        every { photoSearchDomainDataFactory.create(any(), any<PhotoSearchDomainData>()) } returns photoSearchDomainData
        every { photoSearchEventFactory.ready(any()) } returns photoSearchEvent
        every { photoRepository.saveLast(any()) } just Runs
        every { photoRepository.getLast() } returns previousPhotoSearchDomainData

        // When:

        serviceUnderTest.searchMore()
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoApi.search(any(), any())
            photoSearchDtoMapper.mapFrom(any())
            photoSearchDomainDataFactory.create(any(), any<PhotoSearchDomainData>())
            photoRepository.saveLast(any())
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
        val photoSearchDto = PhotoDomainTestDataSet.photoSearchDto_zebra
        val previousPhotoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebra
        val photoSearchDomainData =  PhotoDomainTestDataSet.photoSearchDomainData_zebraMore
        val photosDomainData =  PhotoDomainTestDataSet.photosDomainData_zebra
        val photoSearchEvent = PhotoSearchEvent(photoSearchState, photoSearchDomainData)

        every { photoApi.search(any()) } answers { Single.just(photoSearchDto) }
        every { photoSearchDtoMapper.mapFrom(any()) } returns photosDomainData
        every { photoSearchDomainDataFactory.create(any(), any<PhotoSearchDomainData>()) } returns previousPhotoSearchDomainData
        every { photoSearchEventFactory.ready(any()) } returns photoSearchEvent
        every { photoRepository.saveLast(any()) } just Runs
        every { photoRepository.getLast() } returns null

        // When:

        serviceUnderTest.searchMore()
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoDomainEvents.search wasNot Called
            photoApi.search(any()) wasNot Called
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
        every { photoApi.search(any()) } answers { Single.just(photoSearchDto) }
        every { photoSearchDtoMapper.mapFrom(any()) } returns photosDomainData
        every { photoSearchDomainDataFactory.create(any(), any<String>()) } returns photoSearchDomainData
        every { photoSearchEventFactory.empty() } returns photoSearchEvent
        every { photoRepository.saveLast(any()) } just Runs

        // When:

        serviceUnderTest.search(searchPhrase)
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoApi.search(any())
            photoSearchDtoMapper.mapFrom(any())
            photoRepository.saveLast(any())
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
        every { photoApi.search(any()) } answers { Single.error(Exception()) }
        every { photoSearchEventFactory.error() } returns photoSearchEvent
        every { photoRepository.saveLast(any()) } just Runs

        // When:

        serviceUnderTest.search(searchPhrase)
        schedulers.scheduler.triggerActions()

        // Then:

        verify {
            photoApi.search(any())
            photoSearchEventFactory.error()
            photoDomainEvents.search.onNext(capture(slots))
        }

        assertEquals(slots[0].photoSearchState, photoSearchState)

    }
}