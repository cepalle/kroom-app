package io.kroom.app.view.activitymain.trackvoteevent


import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session

import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent
import io.kroom.app.webservice.GraphClient


class TrackVoteEventsViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client
    private val trackVoteEventRepo = TrackVoteEventRepo(client)


    private val selectedTrackVoteEventPublic: MutableLiveData<EventModel> = MutableLiveData()
    private val selectedTrackVoteEventPrivate: MutableLiveData<EventModel> = MutableLiveData()

    fun getMusicTrackVotePublic(): LiveData<TrackVoteEvent?> {
        return map(trackVoteEventRepo.getTrackVoteEventById(selectedTrackVoteEventPublic.value!!.id)) {
            it.onSuccess {
                return@map it.TrackVoteEventById().trackVoteEvent().let {
                    TrackVoteEvent(
                        it?.id() ?: return@map null,
                        it.userMaster()?.userName() ?: return@map null,
                        it.name(),
                        it.public_(),
                        null,
                        it.trackWithVote() ?: return@map null,
                        0,
                        0,
                        0F,
                        0F,
                        it.userInvited() ?: return@map null
                    )
                }
            }
            it.onFailure {
                //TODO
                return@map null
            }
            return@map null
        }
    }

    fun getTrackVoteEventPublicList(): LiveData<List<EventModel>> {
        return map(trackVoteEventRepo.getTrackVoteEventsPublic()) {
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
        }
    }

    fun getTrackVoteEventPrivateList(): LiveData<List<EventModel>> {
        return map(
            Session.getId(this.getApplication())!!.let {
                trackVoteEventRepo.getTrackVoteEventByUserId(it)
            }
        ) {
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
