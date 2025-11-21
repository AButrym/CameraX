package com.softserveacademy.camerax.data

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.softserveacademy.camerax.data.local.Photo
import com.softserveacademy.camerax.data.local.PhotoDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
    @ApplicationContext private val context: Context
) : PhotoRepository {
    override fun getAllPhotos(): Flow<List<Photo>> = photoDao.getAllPhotos()

    override suspend fun insertPhoto(photo: Photo) {
        photoDao.insertPhoto(photo)
    }

    override suspend fun getGalleryPhotos(): List<Uri> = withContext(Dispatchers.IO) {
        val photos = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                photos.add(contentUri)
            }
        }
        photos
    }

    override suspend fun addAudioToPhoto(photoUri: String, audioUri: String) {
        val photo = photoDao.getPhotoByUri(photoUri)
        photo?.let {
            val updatedPhoto = it.copy(audioUri = audioUri)
            photoDao.updatePhoto(updatedPhoto)
        }
    }
}
