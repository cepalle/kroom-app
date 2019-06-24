package io.kroom.app.view.activitymain.playlisteditor.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.apollographql.apollo.api.Response
import io.kroom.app.repo.PlaylistEditorRepo
import io.kroom.app.repo.webservice.GraphClient
import io.kroom.app.util.SharedPreferences

class PlaylistInvitedViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val playRepo = PlaylistEditorRepo(client)

    val playlistInvited by lazy {
        val id = SharedPreferences.getId(getApplication())
        id?.let {
            playRepo.PlayListEditorByUserId(it)
        }
    }
}