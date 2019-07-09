package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventByIdQuery
import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
import io.kroom.app.graphql.TrackVoteEventsPublicQuery


class TrackVoteEventRepo(private val client: ApolloClient) {

    fun trackVoteEventAddOrUpdateVote(
        eventId: Int,
        userId: Int,
        musicId: Int,
        up: Boolean
    ): LiveData<Result<TrackVoteEventAddOrUpdateVoteMutation.Data>> {
        val data: MutableLiveData<Result<TrackVoteEventAddOrUpdateVoteMutation.Data>> =
            MutableLiveData()

        client.mutate(
            TrackVoteEventAddOrUpdateVoteMutation.builder()
                .eventId(eventId)
                .userId(userId)
                .musicId(musicId)
                .up(up)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun getTrackVoteEventById(
        id: Int
    ): LiveData<Result<TrackVoteEventByIdQuery.Data>> {
        val data: MutableLiveData<Result<TrackVoteEventByIdQuery.Data>> =
            MutableLiveData()

        val queryCall = TrackVoteEventByIdQuery
            .builder()
            .id(id)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun getTrackVoteEventByUserId(
        userId: Int
    ): LiveData<Result<TrackVoteEventByUserIdQuery.Data>> {
        val data: MutableLiveData<Result<TrackVoteEventByUserIdQuery.Data>> =
            MutableLiveData()

        val queryCall = TrackVoteEventByUserIdQuery
            .builder()
            .userId(userId)
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun getTrackVoteEventsPublic(

    ): LiveData<Result<TrackVoteEventsPublicQuery.Data>> {
        val data: MutableLiveData<Result<TrackVoteEventsPublicQuery.Data>> =
            MutableLiveData()

        val queryCall = TrackVoteEventsPublicQuery
            .builder()
            .build()
        client.query(queryCall).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

}