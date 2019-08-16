package io.kroom.app.view.activitymain.trackvoteevent


import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import com.google.android.gms.common.api.Api
import io.kroom.app.graphql.TrackVoteEventByIdQuery
import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session

import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackModel
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent
import io.kroom.app.webservice.GraphClient
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.AbstractMap


class TrackVoteEventsViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client
    private val trackVoteEventRepo = TrackVoteEventRepo(client)


    private val selectedTrackVoteEventPublic: MutableLiveData<EventModel> = MutableLiveData()
    private val selectedTrackVoteEventPrivate: MutableLiveData<EventModel> = MutableLiveData()
    private val selectedEventPublic : MutableLiveData<TrackVoteEvent> = MutableLiveData()
    private val selectedMusicTrackVote: MutableLiveData<TrackModel> = MutableLiveData()
    private val selectedMusicTrackVotePrivate: MutableLiveData<TrackModel> = MutableLiveData()

    private val musicTrackVote: LiveData<Result<TrackVoteEventByIdQuery.Data>> =
        trackVoteEventRepo.getTrackVoteEventById(selectedTrackVoteEventPublic.value!!.id)




    private val eventPublicResult: LiveData<Result<TrackVoteEventsPublicQuery.Data>> =
        trackVoteEventRepo.getTrackVoteEventsPublic()
    /* private val eventPublicResult: LiveData<Result<TrackVoteEventsPublicQuery.Data>> =
         Transformations.map(trackVoteEventRepo.getTrackVoteEventsPublic(), ::getTrackVoteEventPublicList)*/

    private val eventPrivateResult: LiveData<Result<TrackVoteEventByUserIdQuery.Data>> =
        Session.getId(this.getApplication())!!.let {
            trackVoteEventRepo.getTrackVoteEventByUserId(it)
        }


    fun getMusicTrackVotePublic(): LiveData<Any>{
        return map (musicTrackVote) {
            it.onSuccess {
                return@map it.TrackVoteEventById().let{
                    TrackVoteEvent(
                        it.trackVoteEvent()!!.id(),
                        it.trackVoteEvent()!!.userMaster()!!.userName(),
                        it.trackVoteEvent()!!.name(),
                        it.trackVoteEvent()!!.public_(),
                        null,
                        it.trackVoteEvent()!!.trackWithVote()?: return@map null,
                        0,
                        0,
                        0F,
                        0F
                    )
                    it.trackVoteEvent()!!.userInvited()?: return@map null
                }.filterNotNull()
            }
            it.onFailure { //TODO
                return@map it.message
            }
            return@map musicTrackVote
        }
    }



fun getTrackVoteEventPublicList(): LiveData<List<EventModel>> {
    return map(eventPublicResult, {
        it.onSuccess {
            return@map it.TrackVoteEventsPublic().map {
                EventModel(
                    it.id(),
                    it.userMaster()?.id() ?: return@map null,
                    it.userMaster()?.userName() ?: return@map null,
                    it.name(),
                    it.public_(),
                    0,
                    0,
                    8888888F,
                    99999999F
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

fun getTrackVoteEventPrivateList(): LiveData<List<EventModel>> {
    return map(eventPrivateResult) {
        it.onSuccess {
            return@map it.TrackVoteEventByUserId().trackVoteEvents()?.map {
                EventModel(
                    it.id(),
                    it.userMaster()?.id() ?: return@map null,
                    it.userMaster()?.userName() ?: return@map null,
                    it.name(),
                    it.public_(),
                    0,
                    0,
                    0F,
                    0F

                )
            }?.filterNotNull()
        }
        it.onFailure {
            // TODO
            return@map listOf<EventModel>()
        }
        return@map listOf<EventModel>()
    }
}


fun getSelectedTrackVoteEventPublic(): MutableLiveData<EventModel>? {
    return selectedTrackVoteEventPublic
}

fun getSelectedTrackVoteEventPrivate(): MutableLiveData<EventModel>? {
    return selectedTrackVoteEventPrivate
}
}


/* it.TrackVoteEventById().trackVoteEvent()!!.trackWithVote()!!.map {
                            TrackWithVote(
                          TrackModel(
                                    it.track().id() : return@map null,
                                    it.track().title(),
                                    2, ""
                                ),
                                it.score(),
                                it.nb_vote_up(),
                                it.nb_vote_down()
                            ),


                        }*/