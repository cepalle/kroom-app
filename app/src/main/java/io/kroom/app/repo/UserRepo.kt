package io.kroom.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.*
import io.kroom.app.repo.webservice.apolloClient

object UserRepo {

    fun user(
        id: Int
    ): LiveData<Result<Response<UserByIdQuery.Data>>> {
        val data = MutableLiveData<Result<Response<UserByIdQuery.Data>>>()

        apolloClient.query(
            UserByIdQuery.builder()
                .id(id)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun signUp(
        userName: String,
        email: String,
        pass: String
    ): LiveData<Result<Response<UserSignUpMutation.Data>>> {
        val data = MutableLiveData<Result<Response<UserSignUpMutation.Data>>>()

        apolloClient.mutate(
            UserSignUpMutation.builder()
                .userName(userName)
                .email(email)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun signIn(
        userName: String,
        pass: String
    ): LiveData<Result<Response<UserSignInMutation.Data>>> {
        val data = MutableLiveData<Result<Response<UserSignInMutation.Data>>>()

        apolloClient.mutate(
            UserSignInMutation.builder()
                .userName(userName)
                .pass(pass)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun signGoogleRequest(
        token: String
    ): LiveData<Result<Response<UserSignWhithGoolgeMutation.Data>>> {
        val data = MutableLiveData<Result<Response<UserSignWhithGoolgeMutation.Data>>>()

        apolloClient.mutate(
            UserSignWhithGoolgeMutation.builder()
                .token(token)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun addFriend(
        userId: Int,
        friendId: Int
    ): LiveData<Result<Response<UserAddFriendMutation.Data>>> {
        val data = MutableLiveData<Result<Response<UserAddFriendMutation.Data>>>()

        apolloClient.mutate(
            UserAddFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun deleteFriend(
        userId: Int,
        friendId: Int
    ): LiveData<Result<Response<UserDeleteFriendMutation.Data>>> {
        val data = MutableLiveData<Result<Response<UserDeleteFriendMutation.Data>>>()

        apolloClient.mutate(
            UserDeleteFriendMutation.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

    fun userNameAutocompletion(
        prefix: String
    ): LiveData<Result<Response<UserNameAutocompletionQuery.Data>>> {
        val data = MutableLiveData<Result<Response<UserNameAutocompletionQuery.Data>>>()

        apolloClient.query(
            UserNameAutocompletionQuery.builder()
                .prefix(prefix)
                .build()
        ).enqueue(CallBackHandler { data.value = it })

        return data
    }

}