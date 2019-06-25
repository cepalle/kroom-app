package io.kroom.app.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.graphql.PlayListEditorsPublicQuery
import io.reactivex.subjects.BehaviorSubject

class PlaylistEditorRepo(val client: ApolloClient) {

    fun playListEditorsPublic(
    ): BehaviorSubject<Result<Response<PlayListEditorsPublicQuery.Data>>> {
        val data: BehaviorSubject<Result<Response<PlayListEditorsPublicQuery.Data>>> =
            BehaviorSubject.create()

        client.query(
            PlayListEditorsPublicQuery.builder()
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun playListEditorByUserId(
        userId: Int
    ): BehaviorSubject<Result<Response<PlayListEditorByUserIdQuery.Data>>> {
        val data: BehaviorSubject<Result<Response<PlayListEditorByUserIdQuery.Data>>> =
            BehaviorSubject.create()

        client.query(
            PlayListEditorByUserIdQuery.builder()
                .id(userId)
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

}
