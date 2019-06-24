package io.kroom.app.view.activitymain.playlisteditor.tabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.graphql.PlayListEditorsPublicQuery
import io.kroom.app.repo.PlaylistEditorRepo
import io.kroom.app.repo.webservice.GraphClient
import io.kroom.app.util.SharedPreferences
import io.reactivex.disposables.Disposable

class PlaylistPublicViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val playRepo = PlaylistEditorRepo(client)

    private val listPublic = MutableLiveData<Result<Response<PlayListEditorsPublicQuery.Data>>>()
    private var dispose: Disposable? = null

    init {
        val sub = playRepo.playListEditorsPublic()
        dispose = sub.subscribe { r ->
            listPublic.value = r
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispose?.dispose()
    }

    fun getListPublic(): LiveData<Result<Response<PlayListEditorsPublicQuery.Data>>> {
        return listPublic
    }

}