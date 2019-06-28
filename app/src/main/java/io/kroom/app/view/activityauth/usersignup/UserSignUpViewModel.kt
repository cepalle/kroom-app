package io.kroom.app.view.activityauth.usersignup

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.repo.UserRepo
import io.kroom.app.util.SharedPreferences
import io.kroom.app.webservice.GraphClient

class UserSignUpViewModel(app: Application) : AndroidViewModel(app) {

    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val userRepo = UserRepo(client)


    private val result: MutableLiveData<Result<UserSignUpMutation.Data>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun signUp(username: String, email: String, password: String) {
        userRepo.signUp(username, email, password).subscribe { r ->
            result.postValue(r)
        }
    }

    fun getSignUpResult() : LiveData<Result<UserSignUpMutation.Data>> {
        return result
    }


}