package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.kroom.app.graphql.TrackVoteEventDelMutation
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient

class MusicTrackVoteEditorViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val repoTrack = TrackVoteEventRepo(client)

    // fetch on init
    // create model

    fun delTrackVote(eventId: Int): LiveData<Result<TrackVoteEventDelMutation.Data>> {
        return repoTrack.del(eventId)
    }

}
