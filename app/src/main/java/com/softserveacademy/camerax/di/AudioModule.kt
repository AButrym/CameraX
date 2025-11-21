package com.softserveacademy.camerax.di

import android.content.Context
import com.softserveacademy.camerax.domain.AndroidAudioPlayer
import com.softserveacademy.camerax.domain.AndroidAudioRecorder
import com.softserveacademy.camerax.domain.AudioPlayer
import com.softserveacademy.camerax.domain.AudioRecorder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioModule {

    @Provides
    @Singleton
    fun provideAudioRecorder(@ApplicationContext context: Context): AudioRecorder {
        return AndroidAudioRecorder(context)
    }

    @Provides
    @Singleton
    fun provideAudioPlayer(@ApplicationContext context: Context): AudioPlayer {
        return AndroidAudioPlayer(context = context)
    }
}