package io.kroom.app.view.activitymain.trackvoteevent.model

import com.deezer.sdk.model.Artist

data class Track(
    val id: Int,
    val readable: Boolean,
    val title: String,
    val titleShort: String,
    val isrc: String,
    val link: String,
    val share: String,
    val duration: Int,
    val trackPosition: Int,
    val diskNumber: Int,
    val rank: Int,
    val releaseDate: String,
    val explicitLyrics: Boolean,
    val explicitContentLyrics: Int,
    val explicitContentCover: Int,
    val preview: String,
    val bpm: Float,
    val gain: Float,
    val availableCountries: String,
    val contributors: Artist,
    val artistId: Int,
    val albumId: Int,
    val artist: Artist

)