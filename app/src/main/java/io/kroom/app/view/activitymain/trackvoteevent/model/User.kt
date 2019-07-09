package io.kroom.app.view.activitymain.trackvoteevent.model

import com.deezer.sdk.model.Genre

class User (
    val id: Int,
    val userName: String,
    val email: String,
    val latitude: Float,
    val longitude: Float,
    val token: String,
    val privacy: Privacy,
    val friends: User,
    val musicalPreferences: Genre,
    val permissionGroup: String
)