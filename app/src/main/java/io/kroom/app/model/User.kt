package io.kroom.app.model


data class User(val id: Int,
                val userName: String,
                val email:String, val location: String,
                val token: String,
                val privacy: Privacy,
                val friends: User,
                val musicalPreferences: Genre,
                val permissionGroup: String)


