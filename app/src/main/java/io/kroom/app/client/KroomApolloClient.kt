package io.kroom.app.client

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.graphql.*
import io.kroom.app.session.Session

import okhttp3.Interceptor

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

typealias CallBack<T> = (result: T?, exception: ApolloException?) -> Unit

private class CallBackHandler<T>(val res: CallBack<Response<T>>) : ApolloCall.Callback<T>() {
    override fun onResponse(response: Response<T>) {
        res(response, null)
    }

    override fun onFailure(e: ApolloException) {
        res(null, e)
    }
}

object KroomApolloClient {

    const val url = "https:///785dbf4b.ngrok.io/graphql"

    private val okHttpClient: OkHttpClient by lazy {
        fun tokenInterceptor(builder: Interceptor.Chain): okhttp3.Response {
            return if (Session.getToken() != null) {
                builder.proceed(
                    builder.request().newBuilder().header(
                        "Kroom-token-id",
                        Session.getToken()!!
                    ).build()
                )
            } else {
                builder.proceed(builder.request())
            }
        }

        OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(::tokenInterceptor)
            .addNetworkInterceptor {
                it.proceed(
                    it.request().newBuilder().header(
                        "Authorization",
                        "Bearer <TOKEN>"
                    ).build()
                )
            }
            .build()
    }

    private val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(url)
        .okHttpClient(okHttpClient)
        .build()

    object Users {

        fun user(id: Int, res: CallBack<Response<UserByIdQuery.Data>>) {
            apolloClient.query(
                UserByIdQuery.builder()
                    .id(id)
                    .build()
            ).enqueue(CallBackHandler(res))
        }

        data class UserSignUpRequest(val userName: String, val email: String, val pass: String)

        fun signUp(req: UserSignUpRequest, res: CallBack<Response<UserSignUpMutation.Data>>) {
            apolloClient.mutate(
                UserSignUpMutation.builder()
                    .userName(req.userName)
                    .email(req.email)
                    .pass(req.pass)
                    .build()
            ).enqueue(CallBackHandler(res))
        }

        data class UserSignInRequest(val userName: String, val pass: String)

        fun signIn(req: UserSignInRequest, res: CallBack<Response<UserSignInMutation.Data>>) {
            apolloClient.mutate(
                UserSignInMutation.builder()
                    .userName(req.userName)
                    .pass(req.pass)
                    .build()
            ).enqueue(CallBackHandler(res))
        }

        data class UserGoogleSignRequest(val token: String)

        fun signGoogleRequest(
            req: UserGoogleSignRequest,
            res: CallBack<Response<UserSignWhithGoolgeMutation.Data>>
        ) {
            apolloClient.mutate(
                UserSignWhithGoolgeMutation.builder()
                    .token(req.token)
                    .build()
            ).enqueue(CallBackHandler(res))
        }

        fun addFriend(friendId: Int, res: CallBack<Response<UserAddFriendMutation.Data>>) {
            apolloClient.mutate(
                UserAddFriendMutation.builder()
                    .userId(Session.getId()!!)
                    .friendId(friendId)
                    .build()
            ).enqueue(CallBackHandler(res))
        }

        fun deleteFriend(friendId: Int, res: CallBack<Response<UserDeleteFriendMutation.Data>>) {
            apolloClient.mutate(
                UserDeleteFriendMutation.builder()
                    .userId(Session.getId()!!)
                    .friendId(friendId)
                    .build()
            ).enqueue(CallBackHandler(res))
        }

        fun userNameAutocompletion(
            prefix: String,
            res: CallBack<Response<UserNameAutocompletionQuery.Data>>
        ) {
            apolloClient.query(
                UserNameAutocompletionQuery.builder()
                    .prefix(prefix)
                    .build()
            ).enqueue(CallBackHandler(res))
        }
    }

    object TrackVoteEvent {

        data class TrackVoteEventRequest(val eventId: Int, val userId: Int, val musicId: Int, val up: Boolean)

        fun trackVoteEvent(
            req: TrackVoteEventRequest,
            res: CallBack<Response<TrackVoteEventAddOrUpdateVoteMutation.Data>>
        ) {
            apolloClient.mutate(
                TrackVoteEventAddOrUpdateVoteMutation.builder()
                    .eventId(req.eventId)
                    .userId(req.userId)
                    .musicId(req.musicId)
                    .up(req.up)
                    .build()
            ).enqueue(CallBackHandler(res))
        }

        fun getTrackVoteEventById(id: Int, res: CallBack<Response<TrackVoteEventByIdQuery.Data>>) {
            val queryCall = TrackVoteEventByIdQuery
                .builder()
                .id(id)
                .build()
            apolloClient.query(queryCall).enqueue(CallBackHandler(res))
        }

        fun getTrackVoteEventByUserId(
            userId: Int,
            res: CallBack<Response<TrackVoteEventByUserIdQuery.Data>>
        ) {
            val queryCall = TrackVoteEventByUserIdQuery
                .builder()
                .userId(userId)
                .build()
            apolloClient.query(queryCall).enqueue(CallBackHandler(res))
        }

        fun getTrackVoteEventsPublic(res: CallBack<Response<TrackVoteEventsPublicQuery.Data>>) {
            val queryCall = TrackVoteEventsPublicQuery
                .builder()
                .build()
            apolloClient.query(queryCall).enqueue(CallBackHandler(res))
        }
    }

}
