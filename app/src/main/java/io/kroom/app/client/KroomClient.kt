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
import io.kroom.app.graphql.TrackVoteEventById
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


typealias Result<T, E> = (track: T?, exception: E?) -> Unit

class KroomClient {

    companion object {

        var url = "https://d38770e0.ngrok.io/graphql"

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(NetworkInterceptor())
                .build()
        }

        private class NetworkInterceptor : Interceptor {

            override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {
                return chain!!.proceed(chain.request().newBuilder().header("Authorization", "Bearer <TOKEN>").build())
            }
        }

        private val apolloClient = ApolloClient.builder()
            .serverUrl(url)
            .okHttpClient(okHttpClient)
            .build()

    }

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
                TrackVoteEventAddOrUpdateVoteMutation.builder()
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

    fun getTrackVoteEventById(completion: (res: Pair<TrackVoteEventById.Edge?, ApolloException>) -> Unit) {
        val queryCall = TrackVoteEventById
            .builder()
            .id(1)
            .build()
        apolloClient.query(queryCall).enqueue(object : ApolloCall.Callback<TrackVoteEventById.Data>() {
            override fun onFailure(e: ApolloException) {
                completion(Pair(null, Error(e.message)))
            }

            override fun onResponse(response: Response<TrackVoteEventById.Data>) {
                val errors = response.errors()
                if (!errors.isEmpty()) {
                    val message = errors[0]?.message() ?: ""
                    completion(Pair(null, Error(message)))
                } else {
                    completion(Pair(response.data()?.search()?.edges() ?: lazy {  }, null))
                }
            }

        })
    }
}

