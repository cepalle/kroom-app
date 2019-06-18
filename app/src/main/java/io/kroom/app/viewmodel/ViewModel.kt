package io.kroom.app.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.client.KroomClient
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.model.TrackVoteEvent


class ViewModel : ViewModel() {

    private val kroomClient = KroomClient()
    var reposResultTrackVoteEventAddOrUpdateVoteMutation =
        MutableLiveData<Pair<List<TrackVoteEventAddOrUpdateVoteMutation.Data?>, ApolloException?>>()
   var reposResult = MutableLiveData<TrackVoteEventsPublicQuery.TrackVoteEventsPublic>()

    //   var reposResult = MutableLiveData<TrackVoteEventsPublicQuery.TrackVoteEventsPublic?>, ApolloException

   // var repoResult = MutableLiveData<TrackVoteEvent>()

  /*  init {
        getTrackVoteEventByIdVm()
    }*/

    fun getTrackVoteEventByIdVm(id: Int, trackVoteEvent: TrackVoteEvent){
       // kroomClient.getTrackVoteEventById(id,)

    }


}