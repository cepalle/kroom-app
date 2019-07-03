package io.kroom.app.view.activitymain.playlist.invited

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.repo.PlaylistEditorRepo
import io.kroom.app.webservice.GraphClient
import io.kroom.app.util.Session
import io.reactivex.disposables.Disposable

class PlaylistInvitedViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    private val playRepo = PlaylistEditorRepo(client)

    private val listInvited = MutableLiveData<Result<PlayListEditorByUserIdQuery.Data>>()
    private var dispose: Disposable? = null

    init {
        Session.getId(getApplication())?.let {
            dispose = playRepo.playlistEditorByUserId(it).subscribe { r ->
                listInvited.postValue(r)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispose?.dispose()
    }

    fun getListInvited(): LiveData<Result<PlayListEditorByUserIdQuery.Data>> {
        return listInvited
    }

}