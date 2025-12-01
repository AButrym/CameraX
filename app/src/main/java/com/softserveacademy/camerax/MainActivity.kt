package com.softserveacademy.camerax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.softserveacademy.camerax.debug.AudioDebugScreen
import com.softserveacademy.camerax.presentation.MainScreen
import com.softserveacademy.camerax.ui.theme.CameraXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraXTheme {
                MainScreen()
            }
        }
    }
}