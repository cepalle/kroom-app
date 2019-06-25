package io.kroom.app.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventByIdQuery
import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject

class TrackVoteEventRepo(val client: ApolloClient) {

    fun trackVoteEventAddOrUpdateVote(
        eventId: Int,
        userId: Int,
        musicId: Int,
        up: Boolean
    ): Single<Result<TrackVoteEventAddOrUpdateVoteMutation.Data>> {
        val data: SingleSubject<Result<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>> =
            SingleSubject.create()

        client.mutate(
            TrackVoteEventAddOrUpdateVoteMutation.builder()
                .eventId(eventId)
                .userId(userId)
                .musicId(musicId)
                .up(up)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun getTrackVoteEventById(
        id: Int
    ): Single<Result<TrackVoteEventByIdQuery.Data>> {
        val data: SingleSubject<Result<Response<TrackVoteEventByIdQuery.Data>>> =
            SingleSubject.create()

        val queryCall = TrackVoteEventByIdQuery
            .builder()
            .id(id)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun getTrackVoteEventByUserId(
        userId: Int
    ): Single<Result<TrackVoteEventByUserIdQuery.Data>> {
        val data: SingleSubject<Result<Response<TrackVoteEventByUserIdQuery.Data>>> =
            SingleSubject.create()

        val queryCall = TrackVoteEventByUserIdQuery
            .builder()
            .userId(userId)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun getTrackVoteEventsPublic(

    ): Single<Result<TrackVoteEventsPublicQuery.Data>> {
        val data: SingleSubject<Result<Response<TrackVoteEventsPublicQuery.Data>>> =
            SingleSubject.create()

        val queryCall = TrackVoteEventsPublicQuery
            .builder()
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

}