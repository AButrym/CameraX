package com.softserveacademy.camerax

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserveacademy.camerax.data.PhotoRepository
import com.softserveacademy.camerax.data.local.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    fun onPhotoCaptured(uri: String) {
        viewModelScope.launch {
            photoRepository.insertPhoto(Photo(uri = uri))
        }
    }
}