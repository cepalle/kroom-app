package io.kroom.app.view.activitymain.trackvoteevent.musictrackvote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.repo.UserRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient
import io.reactivex.subjects.SingleSubject


class MusicTrackVoteViewModel (app: Application) : AndroidViewModel(app){

    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val trackVoteEventRepo = TrackVoteEventRepo(client)
    private val EventPublicResult: SingleSubject<Result<TrackVoteEventsPublicQuery.Data>> = trackVoteEventRepo.getTrackVoteEventsPublic()

    fun trackVoteEventPublicList() : SingleSubject<Result<TrackVoteEventsPublicQuery.Data>>{
        return EventPublicResult
    }

}