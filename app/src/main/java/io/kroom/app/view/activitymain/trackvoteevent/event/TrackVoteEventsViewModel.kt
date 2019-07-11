package io.kroom.app.view.activitymain.trackvoteevent.event


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient


class TrackVoteEventsViewModel(app: Application) : AndroidViewModel(app) {

    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val trackVoteEventRepo = TrackVoteEventRepo(client)

    fun getTrackVoteEventPublicList(): LiveData<Result<TrackVoteEventsPublicQuery.Data>> {
        return trackVoteEventRepo.getTrackVoteEventsPublic()
    }

}