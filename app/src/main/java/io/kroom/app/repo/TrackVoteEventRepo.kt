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

  /*  data class getTracVoteEventById(
        val trackVoteEventById: LiveData<Result<TrackVoteEventByIdQuery.Data>>,
        val subTrackVote: ApolloSubscriptionCall<TrackVoteEventByIdQuery.Data>
    )
    fun subById(
        trackwithVoteId : Int
    ): getTracVoteEventById{
        val data: MutableLiveData<Result<TrackVoteEventByIdQuery.Data>> = MutableLiveData()
        val subTrackVote: ApolloSubscriptionCall<TrackVoteEventByIdQuery.Data> = client.subscribe(
            TrackVoteEventByIdQuery.builder()
                .id(trackwithVoteId)
                .build()
        )
        subTrackVote.execute(object : ApolloSubscriptionCall.Callback<TrackVoteEventByIdQuery.Data>{
            override fun onResponse(response: Response<TrackVoteEventByIdQuery.Data>){
                data.postValue(
                    if (response.errors().isEmpty()){
                        val dataResponse = response.data()
                        if (dataResponse != null) Result.success(dataResponse)
                        else failure(Throwable("Empty Response"))
                    } else failure(Throwable(response.errors()[0].message()))
                )
            }
            override fun onConnected() {}
            override fun onTerminated() {}
            override fun onCompleted() {}

            override fun onFailure(e: ApolloException) {
                Log.e("ERROR", e.toString())
                data.postValue(failure(e))
            }

        })

        return getTracVoteEventById(data,subTrackVote)
    }*/



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
        public: Boolean
    ): LiveData<Result<TrackVoteEventNewMutation.Data>> {
        val data: MutableLiveData<Result<TrackVoteEventNewMutation.Data>> =
            MutableLiveData()

        client.mutate(
            TrackVoteEventNewMutation.builder()
                .userIdMaster(userIdMaster)
                .name(name)
                .open(public)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }
}
