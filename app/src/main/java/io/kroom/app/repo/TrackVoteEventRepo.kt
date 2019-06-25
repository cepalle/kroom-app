package io.kroom.app.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventByIdQuery
import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.reactivex.subjects.BehaviorSubject

class TrackVoteEventRepo(val client: ApolloClient) {

    fun trackVoteEventAddOrUpdateVote(
        eventId: Int,
        userId: Int,
        musicId: Int,
        up: Boolean
    ): BehaviorSubject<Result<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>> {
        val data: BehaviorSubject<Result<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>> =
            BehaviorSubject.create()

        client.mutate(
            TrackVoteEventAddOrUpdateVoteMutation.builder()
                .eventId(eventId)
                .userId(userId)
                .musicId(musicId)
                .up(up)
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun getTrackVoteEventById(
        id: Int
    ): BehaviorSubject<Result<Response<TrackVoteEventByIdQuery.Data>>> {
        val data: BehaviorSubject<Result<Response<TrackVoteEventByIdQuery.Data>>> =
            BehaviorSubject.create()

        val queryCall = TrackVoteEventByIdQuery
            .builder()
            .id(id)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun getTrackVoteEventByUserId(
        userId: Int
    ): BehaviorSubject<Result<Response<TrackVoteEventByUserIdQuery.Data>>> {
        val data: BehaviorSubject<Result<Response<TrackVoteEventByUserIdQuery.Data>>> =
            BehaviorSubject.create()

        val queryCall = TrackVoteEventByUserIdQuery
            .builder()
            .userId(userId)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun getTrackVoteEventsPublic(

    ): BehaviorSubject<Result<Response<TrackVoteEventsPublicQuery.Data>>> {
        val data: BehaviorSubject<Result<Response<TrackVoteEventsPublicQuery.Data>>> =
            BehaviorSubject.create()

        val queryCall = TrackVoteEventsPublicQuery
            .builder()
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

}