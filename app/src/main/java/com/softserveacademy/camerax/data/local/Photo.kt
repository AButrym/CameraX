package com.softserveacademy.camerax.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This approach currently doesn't support the update of photo uri,
 * if the user changes it in other app
 *
 * > [!IMPORTANT]
 * >
 */
@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uri: String,
    val timestamp: Long = System.currentTimeMillis(),
    val audioUri: String? = null
)