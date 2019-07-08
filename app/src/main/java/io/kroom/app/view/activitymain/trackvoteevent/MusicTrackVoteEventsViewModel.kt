package io.kroom.app.view.activitymain.trackvoteevent


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient
import io.reactivex.disposables.Disposable


class MusicTrackVoteEventsViewModel (app: Application) : AndroidViewModel(app){

    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val trackVoteEventRepo = TrackVoteEventRepo(client)
    private val eventPublicResult: MutableLiveData<Result<TrackVoteEventsPublicQuery.Data>> = MutableLiveData()
    private var dispose: Disposable? = null

    init{
        dispose = trackVoteEventRepo.getTrackVoteEventsPublic().subscribe(){ r ->
            eventPublicResult.postValue(r)
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispose?.dispose()
    }
  //  private val EventListPublicResutl : LiveData<Result<List<TrackVoteEventsPublicQuery.Data>>>? = null

    fun getTrackVoteEventPublicList() : MutableLiveData<Result<TrackVoteEventsPublicQuery.Data>>{
        return eventPublicResult
    }

}