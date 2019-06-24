package io.kroom.app.view.activitymain.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.kroom.app.repo.UserRepo
import io.kroom.app.repo.webservice.GraphClient
import io.kroom.app.util.SharedPreferences

class UserFriendsViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val userRepo = UserRepo(client)


}