package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventByIdQuery
import io.kroom.app.graphql.TrackVoteEventByUserIdQuery
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.webservice.apolloClient

object TrackVoteEventRepo {

    data class TrackVoteEventRequest(val eventId: Int, val userId: Int, val musicId: Int, val up: Boolean)

    fun trackVoteEventAddOrUpdateVote(
        req: TrackVoteEventRequest
    ): LiveData<Result<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>> {
        val data = MutableLiveData<Result<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>>()

        apolloClient.mutate(
            TrackVoteEventAddOrUpdateVoteMutation.builder()
                .eventId(req.eventId)
                .userId(req.userId)
                .musicId(req.musicId)
                .up(req.up)
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