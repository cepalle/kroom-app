package io.kroom.app.repo

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.*
import io.reactivex.subjects.BehaviorSubject

class UserRepo(val client: ApolloClient) {

    fun user(
        id: Int
    ): BehaviorSubject<Result<Response<UserByIdQuery.Data>>> {
        val data: BehaviorSubject<Result<Response<UserByIdQuery.Data>>> =
            BehaviorSubject.create()

        client.query(
            UserByIdQuery.builder()
                .id(id)
                .build()
        ).enqueue(CallBackHandler {
            data.onNext(it)
        })

        return data
    }

    fun signUp(
        userName: String,
        email: String,
        pass: String
    ): BehaviorSubject<Result<Response<UserSignUpMutation.Data>>> {
        val data: BehaviorSubject<Result<Response<UserSignUpMutation.Data>>> =
            BehaviorSubject.create()

        client.mutate(
            UserSignUpMutation.builder()
                .userName(userName)
                .email(email)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun signIn(
        userName: String,
        pass: String
    ): BehaviorSubject<Result<Response<UserSignInMutation.Data>>> {
        val data: BehaviorSubject<Result<Response<UserSignInMutation.Data>>> =
            BehaviorSubject.create()

        client.mutate(
            UserSignInMutation.builder()
                .userName(userName)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun signGoogleRequest(
        token: String
    ): BehaviorSubject<Result<Response<UserSignWhithGoolgeMutation.Data>>> {
        val data: BehaviorSubject<Result<Response<UserSignWhithGoolgeMutation.Data>>> =
            BehaviorSubject.create()

        client.mutate(
            UserSignWhithGoolgeMutation.builder()
                .token(token)
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun addFriend(
        userId: Int,
        friendId: Int
    ): BehaviorSubject<Result<Response<UserAddFriendMutation.Data>>> {
        val data: BehaviorSubject<Result<Response<UserAddFriendMutation.Data>>> =
            BehaviorSubject.create()

        client.mutate(
            UserAddFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun deleteFriend(
        userId: Int,
        friendId: Int
    ): BehaviorSubject<Result<Response<UserDeleteFriendMutation.Data>>> {
        val data: BehaviorSubject<Result<Response<UserDeleteFriendMutation.Data>>> =
            BehaviorSubject.create()

        client.mutate(
            UserDeleteFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.onNext(it) })

        return data
    }

    fun userNameAutocompletion(
        prefix: String
    ): BehaviorSubject<Result<Response<UserNameAutocompletionQuery.Data>>> {
        val data: BehaviorSubject<Result<Response<UserNameAutocompletionQuery.Data>>> =
            BehaviorSubject.create()
        Log.i("TEST", "userNameAutocompletion")

        client.query(
            UserNameAutocompletionQuery.builder()
                .prefix(prefix)
                .build()
        ).enqueue(CallBackHandler {
            Log.i("TEST", "CallBackHandler")

            data.onNext(it)
        })

        return data
    }

}