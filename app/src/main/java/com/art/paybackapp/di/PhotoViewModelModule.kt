package com.art.paybackapp.di

import android.content.res.Resources
import androidx.hilt.navigation.compose.hiltViewModel
import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.data.network.mapper.PhotoSearchDtoMapper
import com.art.paybackapp.data.network.service.PhotoApi
import com.art.paybackapp.domain.PhotoDomain
import com.art.paybackapp.domain.PhotoDomainEvents
import com.art.paybackapp.domain.PhotoSearchEventFactory
import com.art.paybackapp.presentation.screens.detail.PhotoDetailDisplayableFactory
import com.art.paybackapp.presentation.screens.search.PhotoSearchDisplayableFactory
import com.art.paybackapp.presentation.screens.search.PhotoSearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PhotoViewModelModule {

    @Provides
    fun providePhotoDetailDisplayableFactory(
        resources: Resources
    ): PhotoDetailDisplayableFactory {
        return PhotoDetailDisplayableFactory(
            resources = resources
        )
    }

    @Provides
    fun providePhotoDetailSearchDisplayableFactory(
        resources: Resources
    ): PhotoSearchDisplayableFactory {
        return PhotoSearchDisplayableFactory(
            resources = resources
        )
    }

}