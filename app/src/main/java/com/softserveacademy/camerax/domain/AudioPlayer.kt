package com.softserveacademy.camerax.domain

import android.content.Context
import android.media.MediaPlayer
import java.io.File
import android.net.Uri

interface AudioPlayer {
    fun play(file: File)
    fun stop()
}

class AndroidAudioPlayer (
    private val context: Context
) : AudioPlayer {
    private var player: MediaPlayer? = null

    override fun play(file: File) {
        val fileURI = Uri.fromFile(file)
        MediaPlayer.create(context, fileURI).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}