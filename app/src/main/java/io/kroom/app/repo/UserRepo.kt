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
    ): SingleSubject<Result<UserByIdQuery.Data>> {
        val data: SingleSubject<Result<UserByIdQuery.Data>> =
            SingleSubject.create()

        client.query(
            UserByIdQuery.builder()
                .id(id)
                .build()
        ).enqueue(CallBackHandler {
            data.onSuccess(it)
        })

        return data
    }

    fun signUp(
        userName: String,
        email: String,
        pass: String
    ): SingleSubject<Result<UserSignUpMutation.Data>> {
        val data: SingleSubject<Result<UserSignUpMutation.Data>> =
            SingleSubject.create()

        client.mutate(
            UserSignUpMutation.builder()
                .userName(userName)
                .email(email)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun signIn(
        userName: String,
        pass: String
    ): SingleSubject<Result<UserSignInMutation.Data>> {
        val data: SingleSubject<Result<UserSignInMutation.Data>> =
            SingleSubject.create()

        client.mutate(
            UserSignInMutation.builder()
                .userName(userName)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun signGoogleRequest(
        token: String
    ): SingleSubject<Result<UserSignWhithGoolgeMutation.Data>> {
        val data: SingleSubject<Result<UserSignWhithGoolgeMutation.Data>> =
            SingleSubject.create()

        client.mutate(
            UserSignWhithGoolgeMutation.builder()
                .token(token)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun addFriend(
        userId: Int,
        friendId: Int
    ): SingleSubject<Result<UserAddFriendMutation.Data>> {
        val data: SingleSubject<Result<UserAddFriendMutation.Data>> =
            SingleSubject.create()

        client.mutate(
            UserAddFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun deleteFriend(
        userId: Int,
        friendId: Int
    ): SingleSubject<Result<UserDeleteFriendMutation.Data>> {
        val data: SingleSubject<Result<UserDeleteFriendMutation.Data>> =
            SingleSubject.create()

        client.mutate(
            UserDeleteFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun userNameAutocompletion(
        prefix: String
    ): SingleSubject<Result<UserNameAutocompletionQuery.Data>> {
        val data: SingleSubject<Result<UserNameAutocompletionQuery.Data>> =
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

        return data
    }

}