package io.kroom.app.view.activityauth.usersignup

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.view.activityauth.UserSignWithGoogleViewModel

class UserSignUpViewModel(app: Application) : UserSignWithGoogleViewModel(app) {

    private val resultSignUp: MutableLiveData<Result<UserSignUpMutation.Data>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun signUp(username: String, email: String, password: String) {
        userRepo.signUp(username, email, password).subscribe { r ->
            resultSignUp.postValue(r)
        }
    }

    fun getSignUpResult() : LiveData<Result<UserSignUpMutation.Data>> {
        return resultSignUp
    }
}