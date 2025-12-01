package com.softserveacademy.camerax.presentation.gallery

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserveacademy.camerax.data.PhotoRepository
import com.softserveacademy.camerax.domain.AudioPlayer
import com.softserveacademy.camerax.domain.AudioRecorder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class GalleryUiState(
    val photos: List<GalleryPhoto> = emptyList(),
    val isLoading: Boolean = true,
    val isRecording: Boolean = false,
    val isPlaying: Boolean = false
)

data class GalleryPhoto(
    val uri: String,
    val isFromApp: Boolean,
    val audioUri: String? = null
)

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(GalleryUiState())
    val uiState = _uiState.asStateFlow()

    private var audioFile: File? = null

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val galleryPhotos = photoRepository.getGalleryPhotos()
            val appPhotosFlow = photoRepository.getAllPhotos()

            appPhotosFlow.collectLatest { appPhotos ->
                val appPhotosMap = appPhotos.associateBy { it.uri }
                val combinedPhotos = galleryPhotos.map { galleryUri ->
                    val appPhoto = appPhotosMap[galleryUri.toString()]
                    GalleryPhoto(
                        uri = galleryUri.toString(),
                        isFromApp = appPhoto != null,
                        audioUri = appPhoto?.audioUri
                    )
                }
                _uiState.value = GalleryUiState(photos = combinedPhotos, isLoading = false)
            }
        }
    }

    fun startRecording(photoUri: String) {
        audioFile = File(context.cacheDir, "${photoUri.hashCode()}.m4a")
        audioRecorder.start(audioFile!!)
        _uiState.value = _uiState.value.copy(isRecording = true)
    }

    fun stopRecording(photoUri: String) {
        audioRecorder.stop()
        viewModelScope.launch {
            photoRepository.addAudioToPhoto(photoUri, audioFile?.absolutePath ?: "")
        }
        _uiState.value = _uiState.value.copy(isRecording = false)
    }

    fun playAudio(audioUri: String) {
        audioPlayer.play(File(audioUri))
        _uiState.value = _uiState.value.copy(isPlaying = true)
    }

    fun stopAudio() {
        audioPlayer.stop()
        _uiState.value = _uiState.value.copy(isPlaying = false)
    }
}