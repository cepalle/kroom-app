package io.kroom.app.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.graphql.PlayListEditorsPublicQuery
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject

class PlaylistEditorRepo(val client: ApolloClient) {

    fun playListEditorsPublic(
    ): SingleSubject<Result<PlayListEditorsPublicQuery.Data>> {
        val data: SingleSubject<Result<PlayListEditorsPublicQuery.Data>> =
            SingleSubject.create()

        client.query(
            PlayListEditorsPublicQuery.builder()
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun playListEditorByUserId(
        userId: Int
    ): SingleSubject<Result<PlayListEditorByUserIdQuery.Data>> {
        val data: SingleSubject<Result<PlayListEditorByUserIdQuery.Data>> =
            SingleSubject.create()

        client.query(
            PlayListEditorByUserIdQuery.builder()
                .id(userId)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

}
