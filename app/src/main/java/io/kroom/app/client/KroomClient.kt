package io.kroom.app.client

import android.support.annotation.UiThread
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.graphql.*
import io.kroom.app.session.Session
import okhttp3.OkHttpClient


typealias Result<T, E> = (track: T?, exception: E?) -> Unit

object KroomClient {


    var url = "https://2b13c120.ngrok.io/graphql"

    private val okHttpClient = OkHttpClient.Builder().addInterceptor { builder ->
        if (Session.getToken() != null) {
            builder.proceed(builder.request().newBuilder().header("Kroom-token-id", Session.getToken()!!).build())


        } else {
            builder.proceed(builder.request())
        }
    }.build()
    private val apolloClient = ApolloClient.builder()
        .serverUrl(url)
        .okHttpClient(okHttpClient)
        .build()

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
}