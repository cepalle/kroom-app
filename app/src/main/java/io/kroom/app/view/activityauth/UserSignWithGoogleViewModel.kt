package io.kroom.app.view.activityauth

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.kroom.app.R
import io.kroom.app.graphql.UserSignWhithGoolgeMutation
import io.kroom.app.repo.UserRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

typealias GoogleResult = LiveData<Result<UserSignWhithGoolgeMutation.UserSignWithGoogle>>

open class UserSignWithGoogleViewModel(app: Application) : AndroidViewModel(app) {

    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

    protected val userRepo = UserRepo(client)


    private var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private var googleSignInClient: GoogleSignInClient
    private lateinit var googleToken: String

    init {
        googleSignInClient = GoogleSignIn.getClient(app, gso)
    }

    fun signInGoogleIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    @SuppressLint("CheckResult")
    fun getGoogleResult(completedTask: Task<GoogleSignInAccount>): GoogleResult {
        val data = MutableLiveData<Result<UserSignWhithGoolgeMutation.UserSignWithGoogle>>()

        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {

                googleToken = account.idToken.toString()

                userRepo.signGoogleRequest(googleToken).subscribe { r ->
                    r.onFailure {
                        data.postValue(failure(it))
                    }
                    r.onSuccess {
                        val id = it.UserSignWithGoogle().user()?.id()
                        val email = it.UserSignWithGoogle().user()?.email()
                        val userNAme = it.UserSignWithGoogle().user()?.userName()
                        val token = it.UserSignWithGoogle().user()?.token()

                        if (id != null && email != null && userNAme != null && token != null) {
                            Session.setUser(
                                getApplication(),
                                id,
                                email,
                                userNAme,
                                token
                            )
                        }
                        data.postValue(success(it.UserSignWithGoogle()))
                    }
                }
            }
        } catch (e: ApiException) {
            data.postValue(failure(e))
        }

        return data
    }
}