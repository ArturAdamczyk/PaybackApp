package com.art.paybackapp.di

import com.art.paybackapp.BuildConfig
import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.data.network.mapper.PhotoDtoMapper
import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.provider.ApiKeyInterceptor
import com.art.paybackapp.data.network.provider.PhotoServiceProvider
import com.art.paybackapp.data.network.provider.RetrofitPhotoService
import com.art.paybackapp.data.network.service.PhotoApi
import com.art.paybackapp.data.network.service.PhotoService
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.PhotoSearchEventFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
        photoSearchEventFactory: PhotoSearchEventFactory
    ): PhotoDomain {
        return PhotoDomain(
            photoApi = photoApi,
            photoSearchDtoMapper = photoSearchDtoMapper,
            appSchedulers = appSchedulers,
            photoDomainEvents = photoDomainEvents,
            photoSearchEventFactory = photoSearchEventFactory
        )
    }

    @Provides
    fun providePhotoSearchEventFactory()
    : PhotoSearchEventFactory {
        return PhotoSearchEventFactory
    }

    @Provides
    fun providePhotoDomainEvents(
        photoSearchEventFactory: PhotoSearchEventFactory
    ): PhotoDomainEvents {
        return PhotoDomainEvents(
            photoSearchEventFactory = photoSearchEventFactory
        )
    }

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
            .baseUrl("https://pixabay.com/api")
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
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor(BuildConfig.API_KEY)

}