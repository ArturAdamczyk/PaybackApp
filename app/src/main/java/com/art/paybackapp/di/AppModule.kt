package com.art.paybackapp.di

import android.content.Context
import android.content.res.Resources
import com.art.paybackapp.common.AppSchedulers
import com.art.paybackapp.common_android.MainSchedulers
import com.art.paybackapp.common_android.AppLocale
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppSchedulers(): AppSchedulers {
        return MainSchedulers()
    }

    @Provides
    fun provideAppResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    fun provideAppLocale(): AppLocale {
        return AppLocale()
    }

}