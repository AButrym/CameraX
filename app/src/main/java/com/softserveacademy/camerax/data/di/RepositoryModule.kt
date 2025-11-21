package com.softserveacademy.camerax.data.di

import android.content.Context
import com.softserveacademy.camerax.data.PhotoRepository
import com.softserveacademy.camerax.data.PhotoRepositoryImpl
import com.softserveacademy.camerax.data.local.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePhotoRepository(
        photoDao: PhotoDao,
        @ApplicationContext context: Context
    ): PhotoRepository {
        return PhotoRepositoryImpl(photoDao = photoDao, context = context)
    }
}