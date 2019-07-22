package io.kroom.app.view.activitymain.trackvoteevent.event


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
//import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
//import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session
import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.webservice.GraphClient


class TrackVoteEventsViewModel(app: Application) : AndroidViewModel(app) {

    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val trackVoteEventRepo = TrackVoteEventRepo(client)
    private var id: Int = Session.getId(app)!!

    private val selectedTrackVoteEventPublic: MutableLiveData<EventModel> = MutableLiveData()
    private val selectedTrackVoteEventPrivate: MutableLiveData<EventModel> = MutableLiveData()

    private val eventPublicResutl: LiveData<Result<TrackVoteEventsPublicQuery.Data>> = trackVoteEventRepo.getTrackVoteEventsPublic()
    //  private val eventPrivateResult: LiveData<Result<TrackVoteEventByUserIdQuery.Data>> = trackVoteEventRepo.getTrackVoteEventByUserId(id)


   fun getTrackVoteEventPublicList():LiveData<List<EventModel>>{
         return map(eventPublicResutl, {
             it.onSuccess {
                 return@map  it.TrackVoteEventsPublic().map {
                     EventModel(
                         it.id(),
                         it.userMaster()?.userName() ?: return@map null,
                         it.name(),
                         it.public_(),
                         0, 0,
                         it.latitude()?.toFloat() ?: return@map null,
                         it.longitude()?.toFloat() ?: return@map null
                     )
                 }.filterNotNull()
             }
             it.onFailure {
                 // TODO
                 return@map listOf<EventModel>()
             }
             return@map listOf<EventModel>()
         })
     }

    /* fun getTrackVoteEventPrivateList(): LiveData<List<EventModel>>{
         return map(eventPrivateResult) {
             it.onSuccess {
                 return@map  it.TrackVoteEventByUserId(id).trackVoteEvents()?.map{
                     EventModel(
                         it.id(),
                         it.userMaster()?.userName() ?: return@map null,
                                 it.name(),
                         it.public_(),
                         0, 0,
                         it.latitude()?.toFloat() ?: return@map null,
                         it.longitude()?.toFloat() ?: return@map null
                     )
                 }.filterNotNull()
             }
             it.onFailure {
                 // TODO
                 return@map listOf<EventModel>()
             }
             return@map listOf<EventModel>()
         }
     }*/

    fun getSelectedTrackVoteEventPublic(): MutableLiveData<EventModel>? {
        return selectedTrackVoteEventPublic
    }
}


