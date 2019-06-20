package io.kroom.app.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.client.KroomApolloClient
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation
import io.kroom.app.graphql.TrackVoteEventsPublicQuery


class ViewModel : ViewModel() {

    var reposResultEventAddOrUpdateVoteMutation =
        MutableLiveData<Pair<List<TrackVoteEventAddOrUpdateVoteMutation.Data?>, ApolloException?>>()
    var reposResultEventsPublicQuery = MutableLiveData<TrackVoteEventsPublicQuery.TrackVoteEventsPublic>()

    init {
        getTrackVoteEventPublicVm()
    }

    private fun getTrackVoteEventPublicVm() {
        KroomApolloClient.TrackVote.getTrackVoteEventsPublic{
            val handler = Handler(Looper.getMainLooper())
            handler.post{
                    reposResultEventsPublicQuery.apply { it.value }

            }
        }

    }


}