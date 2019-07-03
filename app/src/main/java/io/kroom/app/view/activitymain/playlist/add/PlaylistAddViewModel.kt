package io.kroom.app.view.activitymain.playlist.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.kroom.app.graphql.PlayListEditorNewMutation
import io.kroom.app.repo.PlaylistEditorRepo
import io.kroom.app.webservice.GraphClient
import io.kroom.app.util.Session

class PlaylistAddViewModel(app: Application) : AndroidViewModel(app) {

    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val playRepo = PlaylistEditorRepo(client)

    fun newPlaylist(name: String, public: Boolean): LiveData<Result<PlayListEditorNewMutation.Data>>? {
        return Session.getId(getApplication())?.let {
            playRepo.playlistEditorNew(it, name, public)
        }
    }

}
