package io.kroom.app.view.activityauth.usersignin

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.UserSignInMutation
import io.kroom.app.view.activityauth.UserSignWithGoogleViewModel

class UserSignInViewModel(app: Application) : UserSignWithGoogleViewModel(app) {

    private val resultSignIn: MutableLiveData<Result<UserSignInMutation.Data>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun signIn(username: String, password: String) {
        userRepo.signIn(username, password).subscribe { r ->
            resultSignIn.postValue(r)
        }
    }

    fun getSignInResult(): LiveData<Result<UserSignInMutation.Data>> {
        return resultSignIn
    }
}