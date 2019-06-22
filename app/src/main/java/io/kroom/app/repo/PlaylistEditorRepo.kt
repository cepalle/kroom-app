package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.PlayListEditorsPublicQuery
import io.kroom.app.repo.webservice.apolloClient

object PlaylistEditorRepo {

    fun playListEditorsPublic(
    ): LiveData<Result<Response<PlayListEditorsPublicQuery.Data>>> {
        val data = MutableLiveData<Result<Response<PlayListEditorsPublicQuery.Data>>>()

        apolloClient.query(
            PlayListEditorsPublicQuery.builder()
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

}
