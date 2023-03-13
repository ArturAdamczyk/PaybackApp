package com.art.paybackapp.di

import android.content.res.Resources
import com.art.paybackapp.presentation.screens.detail.PhotoDetailDisplayableFactory
import com.art.paybackapp.presentation.screens.search.PhotoSearchDisplayableFactory
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