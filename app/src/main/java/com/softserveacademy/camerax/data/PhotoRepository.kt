package com.softserveacademy.camerax.data

import android.net.Uri
import com.softserveacademy.camerax.data.local.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getAllPhotos(): Flow<List<Photo>>
    suspend fun insertPhoto(photo: Photo)
    suspend fun getGalleryPhotos(): List<Uri>
    suspend fun addAudioToPhoto(photoUri: String, audioUri: String)
}