package io.kroom.app.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.*
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject

class TrackVoteEventRepo(val client: ApolloClient) {

    fun trackVoteEventAddOrUpdateVote(
        eventId: Int,
        userId: Int,
        musicId: Int,
        up: Boolean
    ): SingleSubject<Result<TrackVoteEventAddOrUpdateVoteMutation.Data>> {
        val data: SingleSubject<Result<TrackVoteEventAddOrUpdateVoteMutation.Data>> =
            SingleSubject.create()

        client.mutate(
            TrackVoteEventAddOrUpdateVoteMutation.builder()
                .eventId(eventId)
                .userId(userId)
                .musicId(musicId)
                .up(up)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun getTrackVoteEventById(
        id: Int
    ): SingleSubject<Result<TrackVoteEventByIdQuery.Data>> {
        val data: SingleSubject<Result<TrackVoteEventByIdQuery.Data>> =
            SingleSubject.create()

        val queryCall = TrackVoteEventByIdQuery
            .builder()
            .id(id)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun getTrackVoteEventByUserId(
        userId: Int
    ): SingleSubject<Result<TrackVoteEventByUserIdQuery.Data>> {
        val data: SingleSubject<Result<TrackVoteEventByUserIdQuery.Data>> =
            SingleSubject.create()

        val queryCall = TrackVoteEventByUserIdQuery
            .builder()
            .userId(userId)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun getTrackVoteEventsPublic(

    ): SingleSubject<Result<TrackVoteEventsPublicQuery.Data>> {
        val data: SingleSubject<Result<TrackVoteEventsPublicQuery.Data>> =
            SingleSubject.create()

        val queryCall = TrackVoteEventsPublicQuery
            .builder()
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun setTrackVoteEventNew(
        userIdMaster: Int,
        name: String,
        public: Boolean
    ): SingleSubject<Result<TrackVoteEventNewMutation.Data>> {
        val data: SingleSubject<Result<TrackVoteEventNewMutation.Data>> =
            SingleSubject.create()

        client.mutate(
            TrackVoteEventNewMutation.builder()
                .userIdMaster(userIdMaster)
                .name(name)
                .open(public)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }
}