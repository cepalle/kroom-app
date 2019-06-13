package io.kroom.app.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.client.KroomClient
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation


class ViewModel : ViewModel() {

    private val kroomClient = KroomClient()
    var reposResult = MutableLiveData<Pair<List<TrackVoteEventAddOrUpdateVoteMutation.Data?>, ApolloException?>>()

    init{
        loadRepository()
    }

    fun loadRepository(){
      //  kroomClient.UsersRepo.trackVoteEvent()
    }
}