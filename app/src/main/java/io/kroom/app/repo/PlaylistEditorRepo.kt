package io.kroom.app.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.graphql.PlayListEditorsPublicQuery
import io.reactivex.subjects.SingleSubject

class PlaylistEditorRepo(val client: ApolloClient) {

    fun playListEditorsPublic(
    ): SingleSubject<Result<Response<PlayListEditorsPublicQuery.Data>>> {
        val data: SingleSubject<Result<Response<PlayListEditorsPublicQuery.Data>>> =
            SingleSubject.create()

        client.query(
            PlayListEditorsPublicQuery.builder()
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun playListEditorByUserId(
        userId: Int
    ): SingleSubject<Result<Response<PlayListEditorByUserIdQuery.Data>>> {
        val data: SingleSubject<Result<Response<PlayListEditorByUserIdQuery.Data>>> =
            SingleSubject.create()

        client.query(
            PlayListEditorByUserIdQuery.builder()
                .id(userId)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

}
