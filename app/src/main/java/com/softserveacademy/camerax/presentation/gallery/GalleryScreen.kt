package com.softserveacademy.camerax.presentation.gallery

import com.softserveacademy.camerax.R
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.softserveacademy.camerax.presentation.gallery.components.PhotoPreview

@Composable
fun GalleryScreen(viewModel: GalleryViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedPhotoUri by remember { mutableStateOf<String?>(null) }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (uiState.photos.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No photos found")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(4.dp)
        ) { 
            items(uiState.photos) { photo ->
                val modifier = if (photo.isFromApp) {
                    Modifier
                        .aspectRatio(1f)
                        .border(4.dp, Color.Red)
                        .clickable { selectedPhotoUri = photo.uri }
                } else {
                    Modifier
                        .aspectRatio(1f)
                        .clickable { selectedPhotoUri = photo.uri }
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo.uri)
                        .crossfade(true)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .build(),
//                        photo.uri,
                    contentDescription = null,
                    modifier = modifier,
                    contentScale = ContentScale.Crop
                )
            }
        }

        selectedPhotoUri?.let { uri ->
            val selectedPhoto = uiState.photos.find { it.uri == uri }
            if (selectedPhoto != null) {
                PhotoPreview(
                    uri = uri,
                    audioUri = selectedPhoto.audioUri,
                    isRecording = uiState.isRecording,
                    isPlaying = uiState.isPlaying,
                    onDismiss = { selectedPhotoUri = null },
                    onActionClick = {
                        if (uiState.isRecording) {
                            viewModel.stopRecording(uri)
                        } else {
                            viewModel.startRecording(uri)
                        }
                    },
                    onPlayClick = { selectedPhoto.audioUri?.let { viewModel.playAudio(it) } },
                    onStopClick = { viewModel.stopAudio() }
                )
            }
        }
    }
}