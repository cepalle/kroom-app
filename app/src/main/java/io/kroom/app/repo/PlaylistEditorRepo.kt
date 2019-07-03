package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.graphql.PlayListEditorNewMutation
import io.kroom.app.graphql.PlayListEditorsPublicQuery

class PlaylistEditorRepo(val client: ApolloClient) {

    fun playlistEditorsPublic(
    ): LiveData<Result<PlayListEditorsPublicQuery.Data>> {
        val data: MutableLiveData<Result<PlayListEditorsPublicQuery.Data>> =
            MutableLiveData()

        client.query(
            PlayListEditorsPublicQuery.builder()
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun playlistEditorByUserId(
        userId: Int
    ): LiveData<Result<PlayListEditorByUserIdQuery.Data>> {
        val data: MutableLiveData<Result<PlayListEditorByUserIdQuery.Data>> =
            MutableLiveData()

        client.query(
            PlayListEditorByUserIdQuery.builder()
                .id(userId)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun playlistEditorNew(
        userMasterId: Int,
        name: String,
        public: Boolean
    ): LiveData<Result<PlayListEditorNewMutation.Data>> {
        val data: MutableLiveData<Result<PlayListEditorNewMutation.Data>> =
            MutableLiveData()

        client.mutate(
            PlayListEditorNewMutation.builder()
                .userMasterId(userMasterId)
                .name(name)
                .publc(public)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

}
