package com.softserveacademy.camerax.presentation.navigation

sealed class Screen(val route: String) {
    object Camera : Screen("camera_screen")
    object Gallery : Screen("gallery_screen")
}