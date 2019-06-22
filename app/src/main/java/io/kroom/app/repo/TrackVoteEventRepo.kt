package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventByIdQuery
import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.repo.webservice.apolloClient

object TrackVoteEventRepo {

    fun trackVoteEventAddOrUpdateVote(
        eventId: Int,
        userId: Int,
        musicId: Int,
        up: Boolean
    ): LiveData<Result<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>> {
        val data = MutableLiveData<Result<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>>()

        apolloClient.mutate(
            TrackVoteEventAddOrUpdateVoteMutation.builder()
                .eventId(eventId)
                .userId(userId)
                .musicId(musicId)
                .up(up)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun getTrackVoteEventById(
        id: Int
    ): LiveData<Result<Response<TrackVoteEventByIdQuery.Data>>> {
        val data = MutableLiveData<Result<Response<TrackVoteEventByIdQuery.Data>>>()

        val queryCall = TrackVoteEventByIdQuery
            .builder()
            .id(id)
            .build()
        apolloClient.query(queryCall).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun getTrackVoteEventByUserId(
        userId: Int
    ): LiveData<Result<Response<TrackVoteEventByUserIdQuery.Data>>> {
        val data = MutableLiveData<Result<Response<TrackVoteEventByUserIdQuery.Data>>>()

        val queryCall = TrackVoteEventByUserIdQuery
            .builder()
            .userId(userId)
            .build()
        apolloClient.query(queryCall).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun getTrackVoteEventsPublic(

    ): LiveData<Result<Response<TrackVoteEventsPublicQuery.Data>>> {
        val data = MutableLiveData<Result<Response<TrackVoteEventsPublicQuery.Data>>>()

        val queryCall = TrackVoteEventsPublicQuery
            .builder()
            .build()
        apolloClient.query(queryCall).enqueue(CallBackHandler { data.value = it })

        return data
    }

}