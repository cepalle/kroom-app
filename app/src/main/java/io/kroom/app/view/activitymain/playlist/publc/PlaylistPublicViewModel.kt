package io.kroom.app.view.activitymain.playlist.publc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.kroom.app.graphql.PlayListEditorsPublicQuery
import io.kroom.app.repo.PlaylistEditorRepo
import io.kroom.app.webservice.GraphClient
import io.kroom.app.util.Session

class PlaylistPublicViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val playRepo = PlaylistEditorRepo(client)

    fun getListPublic(): LiveData<Result<PlayListEditorsPublicQuery.Data>> {
        return playRepo.playlistEditorsPublic()
    }

}