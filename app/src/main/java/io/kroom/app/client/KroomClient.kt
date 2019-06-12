package io.kroom.app.client

import android.support.annotation.UiThread
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main

import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.graphql.UserSignWhithGoolgeMutation
import io.kroom.app.graphql.TrackVoteEventAddOrUpdateVoteMutation

import okhttp3.OkHttpClient


typealias Result<T, E> = (track: T?, exception: E?) -> Unit

object KroomClient {


    var url = "https://0f73d74b.ngrok.io/graphql"

    private val okHttpClient = OkHttpClient.Builder().build()
    private val apolloClient = ApolloClient.builder()
        .serverUrl(url)
        .okHttpClient(okHttpClient)
        .build()

    object UsersRepo {

        data class UserSignUpRequest(val userName: String, val email: String, val pass: String)

        @UiThread
        fun signUp(req: UserSignUpRequest, res: Result<UserSignUpMutation.UserSignUp, ApolloException>) {
            apolloClient.mutate(
                UserSignUpMutation.builder()
                    .userName(req.userName)
                    .email(req.email)
                    .pass(req.pass)
                    .build()
            ).enqueue(object : ApolloCall.Callback<UserSignUpMutation.Data>() {
                override fun onResponse(response: Response<UserSignUpMutation.Data>) {
                    Main.app.runOnUiThread { res(response.data()!!.UserSignUp(), null) }
                }

                override fun onFailure(e: ApolloException) {
                    Main.app.runOnUiThread { res(null, e) }
                }
            })
        }

        data class UserGoogleSignRequest(val token: String)

        @UiThread
        fun signGoogleRequest(
            req: UserGoogleSignRequest,
            res: Result<UserSignWhithGoolgeMutation.UserSignWithGoogle, ApolloException>
        ) {
            apolloClient.mutate(
                UserSignWhithGoolgeMutation.builder()
                    .token(req.token)
                    .build()
            ).enqueue(object : ApolloCall.Callback<UserSignWhithGoolgeMutation.Data>() {
                override fun onResponse(response: Response<UserSignWhithGoolgeMutation.Data>) {
                    Main.app.runOnUiThread { res(response.data()!!.UserSignWithGoogle(), null) }
                }

                override fun onFailure(e: ApolloException) {
                    Main.app.runOnUiThread { res(null, e) }
                }
            })
        }

        data class TrackVoteEventRequest(val eventId: Int, val userId: Int, val musicId: Int, val up: Boolean)

        @UiThread
        fun trackVoteEvent(
            req: TrackVoteEventRequest,
            res: Result<TrackVoteEventAddOrUpdateVoteMutation.TrackVoteEventAddOrUpdateVote, ApolloException>
        ) {
            apolloClient.mutate(
                TrackVoteEventAddOrUpdateVoteMutation.buider()
                    .eventId(req.eventId)
                    .userId(req.userId)
                    .musicId(req.musicId)
                    .up(req.up)
                    .build()
            ).enqueue(object : ApolloCall.Callback<TrackVoteEventAddOrUpdateVoteMutation.Data>() {
                override fun onResponse(response: Response<TrackVoteEventAddOrUpdateVoteMutation.Data>) {
                    Main.app.runOnUiThread {
                        res(response.data()!!.TrackVoteEventAddOrUpdateVote(), null)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Main.app.runOnUiThread { res(null, e) }
                }
            })
        }
    }
}