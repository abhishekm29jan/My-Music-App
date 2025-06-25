package com.example.musicappui.ui.theme

import androidx.annotation.DrawableRes
import com.example.musicappui.R

data class Lib(@DrawableRes val icon : Int, val name: String)

val libraries = listOf<Lib>(
    Lib(R.drawable.baseline_playlist_add_24, "Playlist"),
    Lib(R.drawable.baseline_keyboard_voice_24,"Artist"),
    Lib(R.drawable.outline_album_24,"Album"),
    Lib(R.drawable.ic_music,"Songs"),
    Lib(R.drawable.baseline_queue_music_24,"Genre")
)