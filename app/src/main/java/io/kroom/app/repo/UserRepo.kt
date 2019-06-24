package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.*
import io.reactivex.subjects.SingleSubject

class UserRepo(val client: ApolloClient) {

    fun user(
        id: Int
    ): SingleSubject<Result<Response<UserByIdQuery.Data>>> {
        val data: SingleSubject<Result<Response<UserByIdQuery.Data>>> =
            SingleSubject.create()

        client.query(
            UserByIdQuery.builder()
                .id(id)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

    fun signUp(
        userName: String,
        email: String,
        pass: String
    ): SingleSubject<Result<Response<UserSignUpMutation.Data>>> {
        val data: SingleSubject<Result<Response<UserSignUpMutation.Data>>> =
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
    ): SingleSubject<Result<Response<UserSignInMutation.Data>>> {
        val data: SingleSubject<Result<Response<UserSignInMutation.Data>>> =
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
    ): SingleSubject<Result<Response<UserSignWhithGoolgeMutation.Data>>> {
        val data: SingleSubject<Result<Response<UserSignWhithGoolgeMutation.Data>>> =
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
    ): SingleSubject<Result<Response<UserAddFriendMutation.Data>>> {
        val data: SingleSubject<Result<Response<UserAddFriendMutation.Data>>> =
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
    ): SingleSubject<Result<Response<UserDeleteFriendMutation.Data>>> {
        val data: SingleSubject<Result<Response<UserDeleteFriendMutation.Data>>> =
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
    ): SingleSubject<Result<Response<UserNameAutocompletionQuery.Data>>> {
        val data: SingleSubject<Result<Response<UserNameAutocompletionQuery.Data>>> =
            SingleSubject.create()

        client.query(
            UserNameAutocompletionQuery.builder()
                .prefix(prefix)
                .build()
        ).enqueue(CallBackHandler { data.onSuccess(it) })

        return data
    }

}