package io.kroom.app.view.activitymain.trackvoteevent.event


import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session

import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.webservice.GraphClient


class TrackVoteEventsViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client
    private val trackVoteEventRepo = TrackVoteEventRepo(client)

    private val selectedTrackVoteEventPublic: MutableLiveData<EventModel> = MutableLiveData()
    private val selectedTrackVoteEventPrivate: MutableLiveData<EventModel> = MutableLiveData()

    private val eventPublicResult: LiveData<Result<TrackVoteEventsPublicQuery.Data>> =
        trackVoteEventRepo.getTrackVoteEventsPublic()
    /* private val eventPublicResult: LiveData<Result<TrackVoteEventsPublicQuery.Data>> =
         Transformations.map(trackVoteEventRepo.getTrackVoteEventsPublic(), ::getTrackVoteEventPublicList)*/

    private val eventPrivateResult: LiveData<Result<TrackVoteEventByUserIdQuery.Data>> =
        Session.getId(this.getApplication())!!.let {
            trackVoteEventRepo.getTrackVoteEventByUserId(it)
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
    /*  fun getTrackVoteEventPublicList(r: Result<TrackVoteEventsPublicQuery.Data>): List<EventModel>? {

          if (r == null) return eventPublicResult.value
          r.onFailure {
              Toast.makeText(getApplication(), "liste vide", Toast.LENGTH_SHORT).show()
          }
          r.onSuccess {


              it.TrackVoteEventsPublic().mapNotNull {
                  it.id()
                  it.userMaster()?.id()
                  it.userMaster()?.userName()
                  it.name()
                  it.public_()
                  it.scheduleBegin().toString()
                  it.scheduleEnd().toString()
                  it.latitude()
                  it.longitude()

              }

          }
          return eventPublicResult.value

      }*/
    /*  fun getTrackVoteEventPublicList(): List<EventModel> {
          var public : List<EventModel>
          public = eventPrivateResult.value as List<EventModel>
         return public
      }*/
    /*  private fun updatePlaylistPublic(res: Result<PlayListEditorsPublicQuery.Data>?) {
          if (res == null) return
          res.onFailure {
              Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
          }
          res.onSuccess {
              it.PlayListEditorsPublic().mapNotNull {
                  val userName = it.userMaster()?.userName()
                  val nbTrack = it.tracks()?.count()

                  if (userName != null && nbTrack != null)
                      playAdapterModel(
                          it.id(),
                          it.name(),
                          userName,
                          nbTrack,
                          it.public_()
                      )
                  else null
              }.sortedBy { it.name }
                  .let {
                      adapterPublic?.updateDataSet(it)
                      adapterPublic?.notifyDataSetChanged()
                  }
          }
      }*/


/*fun getTrackVoteEventPrivateList(): LiveData<List<EventModel>>{
    return map(eventPrivateResult) {
        it.onSuccess {
            return@map it.TrackVoteEventByUserId().trackVoteEvents()?.map{
                EventModel(
                    it.id(),
                    it.userMaster()?.id()?: return@map null,
                    it.userMaster()?.userName() ?: return@map null,
                    it.name(),
                    it.public_(),
                    0,
                    0,
                    it.latitude()?.toFloat() ?: return@map null,
                    it.longitude()?.toFloat() ?: return@map null
                )
            }?.filterNotNull()
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

    fun getSelectedTrackVoteEventPrivate(): MutableLiveData<EventModel>? {
        return selectedTrackVoteEventPrivate
    }
}


