package io.kroom.app.model

data class Artist(val id: Int,
                  val name: String,
                  val link: String,
                  val share: String,
                  val picture: String,
                  val pictureSmall: String,
                  val pictureMedium: String,
                  val pictureBig: String,
                  val pictureXl: String,
                  val nbAlbum: Int,
                  val nbFan: Int,
                  val tracklist: String)