package io.kroom.app.view.activitymain.playlist.activityplaylisteditor.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.kroom.app.repo.UserRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class PlaylistEditorOrderViewModelFactory(private val mApplication: Application, private val mParam: Int) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistEditorOrderViewModel(mApplication, mParam) as T
    }
}

class PlaylistEditorOrderViewModel(app: Application, val playlistId: Int) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val userRepo = UserRepo(client)
    private val userId = Session.getId(getApplication())

    private val autoCompletion: MediatorLiveData<List<Pair<String, Int>>> = MediatorLiveData()
    private val trackList: MediatorLiveData<List<Pair<String, Int>>> = MediatorLiveData()
    private val errorMessage: MediatorLiveData<String> = MediatorLiveData()

    private val cacheTrack: MutableSet<Pair<String, Int>> = hashSetOf()

    init {
        userId?.let {
            trackList.addSource(userRepo.user(it)) { r ->
                r.onFailure {
                    errorMessage.postValue(it.message)
                    trackList.postValue(null)
                }
                r.onSuccess {
                    trackList.postValue(
                        it.UserGetById().user()?.friends()?.mapNotNull {
                            val id = it.id()
                            if (id != null) Pair(it.userName(), id)
                            else null
                        }?.map {
                            cacheTrack.add(it)
                            it
                        }
                    )
                }
            }
        }
    }

    fun getAutoCompletion(): LiveData<List<Pair<String, Int>>> {
        return autoCompletion
    }

    fun getTracksList(): LiveData<List<Pair<String, Int>>> {
        return trackList
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

    fun getCacheUSer(): Set<Pair<String, Int>> {
        return cacheTrack
    }


}
