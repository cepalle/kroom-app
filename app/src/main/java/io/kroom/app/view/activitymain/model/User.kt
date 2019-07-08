package io.kroom.app.view.activitymain.model

import com.deezer.sdk.model.Genre

class User (
    val id: Int,
    userName: String,
    email: String,
    latitude: Float,
    longitude: Float,
    token: String,
    privacy: Privacy,
    friends: User,
    musicalPreferences: Genre,
    permissionGroup: String
)