package io.kroom.app.repo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloSubscriptionCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.graphql.*
import kotlin.Result.Companion.failure
import kotlin.coroutines.coroutineContext

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

    fun setTrackVoteEventNew(
        userIdMaster: Int,
        name: String,
        open: Boolean,
        locAndSchRestriction: Boolean,
        scheduleBegin: Long,
        scheduleEnd: Long,
        latitude: Double,
        longitude: Double
    ): LiveData<Result<TrackVoteEventNewMutation.Data>> {
        val data: MutableLiveData<Result<TrackVoteEventNewMutation.Data>> =
            MutableLiveData()

        client.mutate(
            TrackVoteEventNewMutation.builder()
                .userIdMaster(userIdMaster)
                .name(name)
                .open(open)
                .locAndSchRestriction(locAndSchRestriction)
                .scheduleBegin(scheduleBegin.toString())
                .scheduleEnd(scheduleEnd.toString())
                .latitude(latitude)
                .longitude(longitude)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }
}
