package io.kroom.app.view.activitymain.playlist.activityplaylisteditor.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.kroom.app.repo.DeezerRepo
import io.kroom.app.repo.PlaylistEditorRepo


class PlaylistEditorOrderViewModelFactory(private val mApplication: Application, private val mParam: Int) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistEditorOrderViewModel(mApplication, mParam) as T
    }
}

data class TrackListView(
    val title: String,
    val artist: String,
    val id: Int
)

data class AutoCompletView(
    val str: String,
    val id: Int
)

class PlaylistEditorOrderViewModel(app: Application, private val playlistId: Int) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val playlistRepo = PlaylistEditorRepo(client)
    private val deezerRepo = DeezerRepo(client)
    private val userId = Session.getId(getApplication())

    private val autoCompletion: MediatorLiveData<List<AutoCompletView>> = MediatorLiveData()
    private val trackList: MediatorLiveData<List<TrackListView>> = MediatorLiveData()
    private val errorMessage: MediatorLiveData<String> = MediatorLiveData()

    private val cacheTrack: MutableSet<Pair<String, Int>> = hashSetOf()

    init {
        trackList.addSource(playlistRepo.PlayListEditorById(playlistId)) { r ->
            r.onFailure {
                errorMessage.postValue(it.message)
                trackList.postValue(null)
            }
            r.onSuccess {
                trackList.postValue(
                    it.PlayListEditorById().playListEditor()?.tracks()?.mapNotNull {
                        val artist = it.artist()
                        artist ?: return@mapNotNull null
                        TrackListView(it.title(), artist.name(), it.id())
                    }
                )
            }
        }
    }

    fun updateAutoComplet(str: String) {
        autoCompletion.addSource(deezerRepo.search(str)) { r ->
            r.onFailure {
                errorMessage.postValue(it.message)
                autoCompletion.postValue(null)
            }
            r.onSuccess {
                autoCompletion.postValue(
                    it.DeezerSearch().search()?.mapNotNull {
                        val artist = it.artist()
                        artist ?: return@mapNotNull null
                        AutoCompletView(it.title() + artist.name(), it.id())
                    }
                )
            }
        }
    }

    // ---

    fun getAutoCompletion(): LiveData<List<AutoCompletView>> {
        return autoCompletion
    }

    fun getTracksList(): LiveData<List<TrackListView>> {
        return trackList
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

    fun getCacheUSer(): Set<Pair<String, Int>> {
        return cacheTrack
    }

}
