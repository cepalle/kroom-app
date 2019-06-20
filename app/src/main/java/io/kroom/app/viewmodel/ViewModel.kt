package io.kroom.app.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.client.KroomClient
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.model.TrackVoteEvent


class ViewModel : ViewModel() {

    private val kroomClient = KroomClient()
    var reposResultEventAddOrUpdateVoteMutation =
        MutableLiveData<Pair<List<TrackVoteEventAddOrUpdateVoteMutation.Data?>, ApolloException?>>()
   //var reposResultEventsPublicQuery = MutableLiveData<List<TrackVoteEvent>>()
    var reposResultEventsPublicQuery = MutableLiveData<TrackVoteEventsPublicQuery.TrackVoteEventsPublic>()
    //   var reposResult = MutableLiveData<TrackVoteEventsPublicQuery.TrackVoteEventsPublic?>, ApolloException

   // var repoResult = MutableLiveData<TrackVoteEvent>()

    init {
        getTrackVoteEventPublicVm()
    }

    private fun getTrackVoteEventPublicVm() {
        kroomClient.getTrackVoteEventsPublic{
            val handler = Handler(Looper.getMainLooper())
            handler.post{
                    reposResultEventsPublicQuery.apply { it.value }

            }
        }

    }


}