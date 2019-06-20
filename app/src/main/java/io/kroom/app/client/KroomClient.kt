package io.kroom.app.client

import android.support.annotation.UiThread
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.graphql.UserAddFriendMutation
import io.kroom.app.graphql.UserNameAutocompletionQuery
import io.kroom.app.graphql.UserSignInMutation
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.session.Session
import okhttp3.OkHttpClient


typealias Result<T, E> = (track: T?, exception: E?) -> Unit

object KroomClient {


    var url = "https://a72b4d4f.ngrok.io/graphql"

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
        fun userNameAutocompletion(
            prefix: String,
            res: Result<List<UserNameAutocompletionQuery.UserNameAutocompletion>, ApolloException>
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
                    Main.app.runOnUiThread { res(response.data()!!.UserNameAutocompletion(), null) }
                }
            })
        }
    }
}