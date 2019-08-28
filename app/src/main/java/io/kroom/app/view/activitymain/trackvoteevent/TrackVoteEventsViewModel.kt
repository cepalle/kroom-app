package io.kroom.app.view.activitymain.trackvoteevent


import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import io.kroom.app.graphql.DeezerSearchQuery
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.repo.DeezerRepo
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session
import io.kroom.app.view.activitymain.trackvoteevent.model.*

import io.kroom.app.webservice.GraphClient


class TrackVoteEventsViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val trackVoteEventRepo = TrackVoteEventRepo(client)
    private val deezerSearchRepo = DeezerRepo(client)
    private val autoCompletion: MediatorLiveData<List<TrackDictionaryModel>> = MediatorLiveData()
    private val errorMessage: MediatorLiveData<String> = MediatorLiveData()
    private val cacheAutoComplet: MutableMap<String, Int> = mutableMapOf()


    fun getTrackVoteEventById(id: Int): LiveData<TrackVoteEvent?> {
        return map(trackVoteEventRepo.getTrackVoteEventById(id)) {
            it.onSuccess {
                return@map it.TrackVoteEventById().trackVoteEvent().let {
                    TrackVoteEvent(
                        it?.id() ?: return@map null,
                        it.userMaster()?.userName() ?: return@map null,
                        it.name(),
                        it.public_(),
                        it.currentTrack().let {
                            CurrentTrack(
                                it?.id() ?: return@map null,
                                it?.title(),
                                it.album()!!.coverMedium()// TODO change coverMedium
                            )
                        },
                        it.trackWithVote()!!.map {
                            TrackWithVote(
                                it.track().let {
                                    TrackModel(
                                        it.id(),
                                        it.title(),
                                        it.album()?.coverSmall() ?: return@map null,
                                        2,
                                        ""
                                    )
                                },
                                it.score(),
                                it.nb_vote_up(),
                                it.nb_vote_down()
                            )
                        },
                        0,
                        0,
                        0F,
                        0F,
                        it.userInvited()!!.map {
                            User(
                                it.id()!!,
                                it.userName(),
                                it.email()!!
                            )
                        }
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
            Session.getId(getApplication())!!.let {
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

    fun getTrackVoteEventAddOrUpdateVote(
        eventId: Int,
        inputMusic: String,
        up: Boolean): LiveData<Result<TrackVoteEventAddOrUpdateVoteMutation.Data>>?  {

        val userId = Session.getId(getApplication())
        userId ?: return null
        val musicId = cacheAutoComplet[inputMusic]
        musicId ?: return null

        return trackVoteEventRepo.trackVoteEventAddOrUpdateVote(eventId, userId, musicId, true)
    }

    fun updateTrackDictionary(str: String) {
        autoCompletion.addSource(deezerSearchRepo.search(str)) { r ->
            r.onFailure {
                errorMessage.postValue(it.message)
                autoCompletion.postValue(null)
            }
            r.onSuccess {
                autoCompletion.postValue(
                    it.DeezerSearch().search()?.mapNotNull {
                        TrackDictionaryModel("${it.title()} / ${it.artistName()}", it.id())
                    }?.map {
                        cacheAutoComplet[it.str] = it.id
                        it
                    }
                )
            }
        }
    }

    fun getAutoCompletion() : LiveData<List<TrackDictionaryModel>> {
        return autoCompletion
    }

}
