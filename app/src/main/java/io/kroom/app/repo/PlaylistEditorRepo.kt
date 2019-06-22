package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.PlayListEditorsPublicQuery

class PlaylistEditorRepo(val client: ApolloClient) {

    fun playListEditorsPublic(
    ): LiveData<Result<Response<PlayListEditorsPublicQuery.Data>>> {
        val data = MutableLiveData<Result<Response<PlayListEditorsPublicQuery.Data>>>()

        client.query(
            PlayListEditorsPublicQuery.builder()
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

}
