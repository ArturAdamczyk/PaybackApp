package com.art.paybackapp

import android.app.Application
import com.art.paybackapp.data.network.mapper.PhotoDtoMapper
import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.provider.ApiKeyInterceptor
import com.art.paybackapp.data.network.service.PhotoApi
import com.art.paybackapp.data.network.service.PhotoService
import com.art.paybackapp.data.network.provider.PhotoServiceProvider
import com.art.paybackapp.data.network.provider.RetrofitPhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@HiltAndroidApp
class PaybackApp : Application()

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {

    @Provides
    fun providePhotoApi(
        photoServiceProvider: PhotoServiceProvider,
        photoSearchDtoMapper: PhotoSearchDtoMapper
    ): PhotoApi {
        return PhotoService(
            photoServiceProvider = photoServiceProvider,
            photoSearchDtoMapper =  photoSearchDtoMapper
        )
    }

    @Provides
    fun providePhotoSearchDtoMapper(): PhotoSearchDtoMapper {
        return PhotoSearchDtoMapper()
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
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor("key")

}

