package io.kroom.app.client

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.UiThread
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.graphql.*

import okhttp3.Interceptor

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


typealias Result<T, E> = (track: T?, exception: E?) -> Unit

class KroomClient {

    companion object {

        var url = "https://2b13c120.ngrok.io/graphql"

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor { builder ->
                    if (Session.getToken() != null) {
                        builder.proceed(builder.request().newBuilder().header("Kroom-token-id", Session.getToken()!!).build())


                    } else {
                        builder.proceed(builder.request())
                    }
                }
                .addNetworkInterceptor(NetworkInterceptor())
                .build()
        }

    private val okHttpClient = OkHttpClient.Builder().build()
    private val apolloClient = ApolloClient.builder()
        .serverUrl(url)
        .okHttpClient(okHttpClient)
        .build()

    }

    object Users {

        @UiThread
        fun user(id: Int, res: Result<Response<UserByIdQuery.Data>, ApolloException>) {
            apolloClient.query(
                UserByIdQuery.builder()
                    .id(id)
                    .build()
            ).enqueue(object : ApolloCall.Callback<UserByIdQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Main.app.runOnUiThread { res(null, e) }
                }

                override fun onResponse(response: Response<UserByIdQuery.Data>) {
                    Main.app.runOnUiThread { res(response, null) }
                }
            })
        }

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

        data class UserSignInRequest(val userName: String, val pass: String)

        @UiThread
        fun signIn(req: UserSignInRequest, res: Result<UserSignInMutation.UserSignIn, ApolloException>) {
            apolloClient.mutate(
                UserSignInMutation.builder()
                    .userName(req.userName)
                    .pass(req.pass)
                    .build()
            ).enqueue(object : ApolloCall.Callback<UserSignInMutation.Data>() {
                override fun onResponse(response: Response<UserSignInMutation.Data>) {
                    Main.app.runOnUiThread { res(response.data()!!.UserSignIn(), null) }
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

        @UiThread
        fun addFriend(friendId: Int, res: Result<UserAddFriendMutation.UserAddFriend, ApolloException>) {
            apolloClient.mutate(
                UserAddFriendMutation.builder()
                    .userId(Session.getId()!!)
                    .friendId(friendId)
                    .build()
            ).enqueue(object : ApolloCall.Callback<UserAddFriendMutation.Data>() {
                override fun onResponse(response: Response<UserAddFriendMutation.Data>) {
                    Main.app.runOnUiThread { res(response.data()!!.UserAddFriend(), null) }
                }

                override fun onFailure(e: ApolloException) {
                    Main.app.runOnUiThread { res(null, e) }
                }
            })
        }

        @UiThread
        fun deleteFriend(friendId: Int, res: Result<UserDeleteFriendMutation.UserDelFriend, ApolloException>) {
            apolloClient.mutate(
                UserDeleteFriendMutation.builder()
                    .userId(Session.getId()!!)
                    .friendId(friendId)
                    .build()
            ).enqueue(object : ApolloCall.Callback<UserDeleteFriendMutation.Data>() {
                override fun onResponse(response: Response<UserDeleteFriendMutation.Data>) {
                    Main.app.runOnUiThread { res(response.data()!!.UserDelFriend(), null) }
                }

                override fun onFailure(e: ApolloException) {
                    Main.app.runOnUiThread { res(null, e) }
                }
            })
        }


        @UiThread
        fun userNameAutocompletion(
            prefix: String,
            res: Result<Response<UserNameAutocompletionQuery.Data>, ApolloException>
        ) {
            apolloClient.query(
                UserNameAutocompletionQuery.builder()
                    .prefix(prefix)
                    .build()
            ).enqueue(object : ApolloCall.Callback<UserNameAutocompletionQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Main.app.runOnUiThread { res(null, e) }
                }

                override fun onResponse(response: Response<UserNameAutocompletionQuery.Data>) {
                    Main.app.runOnUiThread { res(response, null) }
                }
            })
        }

    }

    @UiThread
    fun getTrackVoteEventById(id: Int, res: Result<TrackVoteEventByIdQuery.TrackVoteEvent, ApolloException>) {
        val queryCall = TrackVoteEventByIdQuery
            .builder()
            .id(id)
            .build()
        apolloClient.query(queryCall).enqueue(object : ApolloCall.Callback<TrackVoteEventByIdQuery.Data>() {

            override fun onResponse(response: Response<TrackVoteEventByIdQuery.Data>) {
                res(response.data()!!.TrackVoteEventById().trackVoteEvent(), null)
            }

            override fun onFailure(e: ApolloException) {
                Main.app.runOnUiThread { res(null, e) }
            }

        })
    }

    @UiThread
    fun getTrackVoteEventByUserId(
        userId: Int,
        res: Result<TrackVoteEventByUserIdQuery.TrackVoteEventByUserId, ApolloException>
    ) {
        val queryCall = TrackVoteEventByUserIdQuery
            .builder()
            .userId(userId)
            .build()
        apolloClient.query(queryCall).enqueue(object : ApolloCall.Callback<TrackVoteEventByUserIdQuery.Data>() {

            override fun onResponse(response: Response<TrackVoteEventByUserIdQuery.Data>) {
                res(response.data()!!.TrackVoteEventByUserId(), null)
            }

            override fun onFailure(e: ApolloException) {
                Main.app.runOnUiThread { res(null, e) }
            }
        })
    }


    @UiThread
    fun getTrackVoteEventsPublic (res: (MutableLiveData<TrackVoteEventsPublicQuery.TrackVoteEventsPublic?>) -> Unit)  {
        val queryCall = TrackVoteEventsPublicQuery
            .builder()
            .build()
        apolloClient.query(queryCall).enqueue(object : ApolloCall.Callback<TrackVoteEventsPublicQuery.Data?>() {


            override fun onResponse(response: Response<TrackVoteEventsPublicQuery.Data?>) {
                Pair(response.data()?.TrackVoteEventsPublic()?: listOf(), null)
            }

            override fun onFailure(e: ApolloException) {
               // Main.app.runOnUiThread { res(null, e) }
            }

        })

    }
}