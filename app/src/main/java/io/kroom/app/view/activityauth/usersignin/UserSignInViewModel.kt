package io.kroom.app.view.activityauth.usersignin

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.UserSignInMutation
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.repo.UserRepo
import io.kroom.app.util.SharedPreferences
import io.kroom.app.webservice.GraphClient

class UserSignInViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val userRepo = UserRepo(client)


    private val result: MutableLiveData<Result<UserSignInMutation.Data>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun signIn(username: String, password: String) {
        userRepo.signIn(username, password).subscribe { r ->
            result.postValue(r)
        }
    }

    fun getSignInResult() : LiveData<Result<UserSignInMutation.Data>> {
        return result
    }
}