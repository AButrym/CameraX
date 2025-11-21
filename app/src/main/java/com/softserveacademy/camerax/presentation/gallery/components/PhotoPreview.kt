package com.softserveacademy.camerax.presentation.gallery.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage

@Composable
fun PhotoPreview(
    uri: String,
    audioUri: String?,
    isRecording: Boolean,
    isPlaying: Boolean,
    onDismiss: () -> Unit,
    onActionClick: () -> Unit,
    onPlayClick: () -> Unit,
    onStopClick: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // Handle permission denial
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.9f))
                .clickable { onDismiss() } // Dismiss on background click
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected photo preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Fit
                )
                Row {
                    Button(
                        onClick = onActionClick,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(if (isRecording) "Stop Recording" else "Start Recording")
                    }
                    audioUri?.let {
                        Button(
                            onClick = { if (isPlaying) onStopClick() else onPlayClick() },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(if (isPlaying) "Stop Audio" else "Play Audio")
                        }
                    }
                }
            }
        }
    }
}