package com.softserveacademy.camerax.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softserveacademy.camerax.presentation.camera.CameraScreen
import com.softserveacademy.camerax.presentation.gallery.GalleryScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, 
        startDestination = Screen.Camera.route,
        modifier = modifier
    ) {
        composable(Screen.Camera.route) {
            CameraScreen(navController = navController)
        }
        composable(Screen.Gallery.route) {
            GalleryScreen()
        }
    }
}