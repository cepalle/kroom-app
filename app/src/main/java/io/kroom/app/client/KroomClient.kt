package io.kroom.app.client

import android.support.annotation.UiThread
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.graphql.UserSignInMutation
import io.kroom.app.graphql.UserSignUpMutation
import okhttp3.OkHttpClient


typealias Result<T, E> = (track: T?, exception: E?) -> Unit

object KroomClient {


    var url = "https://50ba15ac.ngrok.io/graphql"

    private val okHttpClient = OkHttpClient.Builder().build()
    private val apolloClient = ApolloClient.builder()
        .serverUrl(url)
        .okHttpClient(okHttpClient)
        .build()

    object Users {

        data class UserSignUpRequest(val userName: String,val email: String, val pass: String )

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

        data class UserSignInRequest(val userName: String, val pass: String )

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
    }
}