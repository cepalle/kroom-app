package io.kroom.app.view.activitymain.playlisteditor.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.repo.PlaylistEditorRepo
import io.kroom.app.webservice.GraphClient
import io.kroom.app.util.SharedPreferences
import io.reactivex.disposables.Disposable

class PlaylistInvitedViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val playRepo = PlaylistEditorRepo(client)

    private val listInvited = MutableLiveData<Result<Response<PlayListEditorByUserIdQuery.Data>>>()
    private var dispose: Disposable? = null

    init {
        SharedPreferences.getId(getApplication())?.let {
            dispose = playRepo.playListEditorByUserId(it).subscribe { r ->
                listInvited.value = r
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispose?.dispose()
    }

    fun getListInvited(): LiveData<Result<Response<PlayListEditorByUserIdQuery.Data>>> {
        return listInvited
    }

}