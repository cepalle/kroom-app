package io.kroom.app.view.activitymain.trackvoteevent.event.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.TrackVoteEventNewMutation
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient

class TrackVoteEventListAddViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val eventRepo = TrackVoteEventRepo(client)

    fun newPlaylist(name: String, public: Boolean): LiveData<Result<TrackVoteEventNewMutation.Data>>? {
        return Session.getId(getApplication())?.let {
            eventRepo.setTrackVoteEventNew(it, name, public)
        }
    }
}