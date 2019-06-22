package io.kroom.app.view.main.playlisteditor.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.kroom.app.repo.PlaylistEditorRepo
import io.kroom.app.repo.webservice.GraphClient
import io.kroom.app.util.SharedPreferences

class PlaylistPublicViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val playRepo = PlaylistEditorRepo(client)

    val playlistPublic = playRepo.playListEditorsPublic()
}