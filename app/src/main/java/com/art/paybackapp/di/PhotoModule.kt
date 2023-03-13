package com.art.paybackapp.di

import com.art.paybackapp.BuildConfig
import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.data.db.SimpleInMemoryDatabase
import com.art.paybackapp.data.network.mapper.PhotoDtoMapper
import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.provider.ApiKeyInterceptor
import com.art.paybackapp.data.network.provider.LocaleInterceptor
import com.art.paybackapp.data.network.provider.PhotoServiceProvider
import com.art.paybackapp.data.network.provider.RetrofitPhotoService
import com.art.paybackapp.data.network.service.PhotoApi
import com.art.paybackapp.data.network.service.PhotoService
import com.art.paybackapp.data.repository.PhotoRepository
import com.art.paybackapp.data.repository.PhotoRepositoryImpl
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.PhotoSearchEventFactory
import com.art.paybackapp.domain.model.PhotoSearchDomainDataFactory
import com.art.paybackapp.utils.AppLocale
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {

    @Provides
    fun providePhotoDomain(
        photoApi: PhotoApi,
        photoSearchDtoMapper: PhotoSearchDtoMapper,
        appSchedulers: AppSchedulers,
        photoDomainEvents: PhotoDomainEvents,
        photoSearchEventFactory: PhotoSearchEventFactory,
        photoRepository: PhotoRepository,
        photoDomainDataFactory: PhotoSearchDomainDataFactory
    ): PhotoDomain {
        return PhotoDomain(
            photoApi = photoApi,
            photoSearchDtoMapper = photoSearchDtoMapper,
            appSchedulers = appSchedulers,
            photoDomainEvents = photoDomainEvents,
            photoSearchEventFactory = photoSearchEventFactory,
            photoRepository = photoRepository,
            photoDomainDataFactory = photoDomainDataFactory
        )
    }

    @Provides
    fun providePhotoDomainDataFactory()
            : PhotoSearchDomainDataFactory {
        return PhotoSearchDomainDataFactory
    }

    @Provides
    fun providePhotoSearchEventFactory()
            : PhotoSearchEventFactory {
        return PhotoSearchEventFactory
    }

    @Singleton
    @Provides
    fun providePhotoDomainEvents(): PhotoDomainEvents = PhotoDomainEvents()

    @Provides
    fun providePhotoApi(
        photoServiceProvider: PhotoServiceProvider
    ): PhotoApi {
        return PhotoService(
            photoServiceProvider = photoServiceProvider
        )
    }

    @Provides
    fun providePhotoSearchDtoMapper(
        photoDtoMapper: PhotoDtoMapper
    ): PhotoSearchDtoMapper {
        return PhotoSearchDtoMapper(
            photoDtoMapper = photoDtoMapper
        )
    }

    @Provides
    fun providePhotoDtoMapper(): PhotoDtoMapper {
        return PhotoDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRetrofitPhotoService(
        okHttpClient: OkHttpClient
    ): RetrofitPhotoService {
        return Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitPhotoService::class.java)
    }

    @Provides
    fun providePhotoServiceProvider(retrofitPhotoService: RetrofitPhotoService): PhotoServiceProvider {
        return PhotoServiceProvider(
            photoService = retrofitPhotoService
        )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        localeInterceptor: LocaleInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(localeInterceptor)
            .build()
    }

    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor(BuildConfig.API_KEY)

    @Provides
    fun provideLocaleInterceptor(
        appLocale: AppLocale
    ): LocaleInterceptor = LocaleInterceptor(appLocale)

    @Singleton
    @Provides
    fun provideSimpleInMemoryDatabase(): SimpleInMemoryDatabase = SimpleInMemoryDatabase()

    @Provides
    fun providePhotoRepository(
        simpleInMemoryDatabase: SimpleInMemoryDatabase
    ): PhotoRepository =
        PhotoRepositoryImpl(
            simpleInMemoryDatabase = simpleInMemoryDatabase
        )

}