package io.kroom.app.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import io.kroom.app.graphql.*


class UserRepo(private val client: ApolloClient) {

    fun user(
        id: Int
    ): LiveData<Result<UserByIdQuery.Data>> {
        val data: MutableLiveData<Result<UserByIdQuery.Data>> =
            MutableLiveData()

        client.query(
            UserByIdQuery.builder()
                .id(id)
                .build()
        ).enqueue(CallBackHandler {
            data.postValue(it)
        })

        return data
    }

    fun signUp(
        userName: String,
        email: String,
        pass: String
    ): LiveData<Result<UserSignUpMutation.Data>> {
        val data: MutableLiveData<Result<UserSignUpMutation.Data>> =
            MutableLiveData()

        client.mutate(
            UserSignUpMutation.builder()
                .userName(userName)
                .email(email)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun signIn(
        userName: String,
        pass: String
    ): LiveData<Result<UserSignInMutation.Data>> {
        val data: MutableLiveData<Result<UserSignInMutation.Data>> =
            MutableLiveData()

        client.mutate(
            UserSignInMutation.builder()
                .userName(userName)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun signGoogleRequest(
        token: String
    ): LiveData<Result<UserSignWhithGoolgeMutation.Data>> {
        val data: MutableLiveData<Result<UserSignWhithGoolgeMutation.Data>> =
            MutableLiveData()

        client.mutate(
            UserSignWhithGoolgeMutation.builder()
                .token(token)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun addFriend(
        userId: Int,
        friendId: Int
    ): LiveData<Result<UserAddFriendMutation.Data>> {
        val data: MutableLiveData<Result<UserAddFriendMutation.Data>> =
            MutableLiveData()

        client.mutate(
            UserAddFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun deleteFriend(
        userId: Int,
        friendId: Int
    ): LiveData<Result<UserDeleteFriendMutation.Data>> {
        val data: MutableLiveData<Result<UserDeleteFriendMutation.Data>> =
            MutableLiveData()

        client.mutate(
            UserDeleteFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.postValue(it) })

        return data
    }

    fun userNameAutocompletion(
        prefix: String
    ): LiveData<Result<UserNameAutocompletionQuery.Data>> {
        val data: MutableLiveData<Result<UserNameAutocompletionQuery.Data>> =
            MutableLiveData()
        Log.i("TEST", "userNameAutocompletion")

        client.query(
            UserNameAutocompletionQuery.builder()
                .prefix(prefix)
                .build()
        ).enqueue(CallBackHandler {
            Log.i("TEST", "CallBackHandler")

            data.postValue(it)
        })

        return data
    }

}