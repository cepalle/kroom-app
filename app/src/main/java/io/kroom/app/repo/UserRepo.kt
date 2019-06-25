package io.kroom.app.repo

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.*
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject

class UserRepo(val client: ApolloClient) {

    fun user(
        id: Int
    ): Single<Result<UserByIdQuery.Data>> {
        val data: SingleSubject<Result<Response<UserByIdQuery.Data>>> =
            SingleSubject.create()

        client.query(
            UserByIdQuery.builder()
                .id(id)
                .build()
        ).enqueue(CallBackHandler {
            data.onSuccess(it)
        })

        return data.map(::flatenResultResponse)
    }

    fun signUp(
        userName: String,
        email: String,
        pass: String
    ): Single<Result<UserSignUpMutation.Data>> {
        val data: SingleSubject<Result<Response<UserSignUpMutation.Data>>> =
            SingleSubject.create()

        client.mutate(
            UserSignUpMutation.builder()
                .userName(userName)
                .email(email)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun signIn(
        userName: String,
        pass: String
    ): Single<Result<UserSignInMutation.Data>> {
        val data: SingleSubject<Result<Response<UserSignInMutation.Data>>> =
            SingleSubject.create()

        client.mutate(
            UserSignInMutation.builder()
                .userName(userName)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun signGoogleRequest(
        token: String
    ): Single<Result<UserSignWhithGoolgeMutation.Data>> {
        val data: SingleSubject<Result<Response<UserSignWhithGoolgeMutation.Data>>> =
            SingleSubject.create()

        client.mutate(
            UserSignWhithGoolgeMutation.builder()
                .token(token)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun addFriend(
        userId: Int,
        friendId: Int
    ): Single<Result<UserAddFriendMutation.Data>> {
        val data: SingleSubject<Result<Response<UserAddFriendMutation.Data>>> =
            SingleSubject.create()

        client.mutate(
            UserAddFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun deleteFriend(
        userId: Int,
        friendId: Int
    ): Single<Result<UserDeleteFriendMutation.Data>> {
        val data: SingleSubject<Result<Response<UserDeleteFriendMutation.Data>>> =
            SingleSubject.create()

        client.mutate(
            UserDeleteFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data.map(::flatenResultResponse)
    }

    fun userNameAutocompletion(
        prefix: String
    ): Single<Result<UserNameAutocompletionQuery.Data>> {
        val data: SingleSubject<Result<Response<UserNameAutocompletionQuery.Data>>> =
            SingleSubject.create()
        Log.i("TEST", "userNameAutocompletion")

        client.query(
            UserNameAutocompletionQuery.builder()
                .prefix(prefix)
                .build()
        ).enqueue(CallBackHandler {
            Log.i("TEST", "CallBackHandler")

            data.onSuccess(it)
        })

        return data.map(::flatenResultResponse)
    }

}