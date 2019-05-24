package io.kroom.app.idp

import android.app.Activity
import android.content.Intent
import android.text.method.TextKeyListener.clear
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.jk.simple.SimpleAuthResult
import com.jk.simple.SimpleAuthResultCallback
import com.jk.simple.SimpleAuthprovider
import com.jk.simple.idp.IdpType
import io.kroom.app.idp.AuthClient

class GoogleAuthClient : AuthClient() {
    private val TAG = "[GoogleAuthClient]"

    private val GOOGLE_AUTH_SDK_BASE_ERROR = -140
    private val GOOGLE_LOGIN_REQUEST = 12121

    private var googleSignInClient: GoogleSignInClient? = null
    private var googleSignInAccount: GoogleSignInAccount? = null

    private var loginCallback: SimpleAuthResultCallback<Void>? = null

    override fun login(activity: Activity, callback: SimpleAuthResultCallback<Void>) {
        googleInit(activity, SimpleAuthResultCallback<Void> { googleInitResult ->
            if (googleInitResult.isSuccess) {
                val lastAccount = GoogleSignIn.getLastSignedInAccount(activity)

                if (lastAccount != null) {
                    val googlesinginclient = googleSignInClient

                        googlesinginclient?.silentSignIn()?.addOnCompleteListener { task -> handleGoogleSignInAccount(task, callback) }

                } else {
                    loginCallback = callback

                    val googleLoginIntent = googleSignInClient?.getSignInIntent()
                    activity.startActivityForResult(googleLoginIntent, GOOGLE_LOGIN_REQUEST)
                }
            } else {
                callback.onResult(googleInitResult)
            }
        })
    }

    private fun googleInit(mActivity: Activity, callback: SimpleAuthResultCallback<Void>) {
        if (SimpleAuthprovider.getInstance().getServerId(IdpType.GOOGLE) == null) {
            callback.onResult(
                SimpleAuthResult.getFailResult(AUTH_CLIENT_PROVIDER_ERROR,
                    "SERVER_ID_NULL"
                )
            )
        } else {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(SimpleAuthprovider.getInstance().getServerId(IdpType.GOOGLE))
                .build()

            googleSignInClient = GoogleSignIn.getClient(mActivity, gso)

            if (googleSignInClient == null) {
                callback.onResult(
                    SimpleAuthResult.getFailResult(AUTH_CLIENT_INIT_ERROR,
                        "FAIL_TO_INIT_GOOGLE_CLIENT"
                    )
                )
            } else {
                callback.onResult(SimpleAuthResult.getSuccessResult(null))
            }
        }
    }

    override fun logout() {
        googleSignInClient?.signOut()
        clear()
    }

    private fun clear() {
        googleSignInClient = null
        googleSignInAccount = null
    }



    override fun getToken(): String {
        val idtoken = googleSignInAccount?.idToken
        return if (idtoken != null) idtoken else "";
    }

    override fun getEmail(): String {
        val getmail = googleSignInAccount?.email
        return if (getmail != null) getmail else ""
    }

    override fun getIdpType(): IdpType {
        return IdpType.GOOGLE
    }

    override fun isSignedIn(activity: Activity): Boolean {
        return if (googleSignInAccount != null) GoogleSignIn.getLastSignedInAccount(activity) != null else false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == GOOGLE_LOGIN_REQUEST) {
            if (loginCallback != null) {
                handleGoogleSignInAccount(GoogleSignIn.getSignedInAccountFromIntent(data), loginCallback)
            }
        }
    }

    private fun handleGoogleSignInAccount(task: Task<GoogleSignInAccount>, callback: SimpleAuthResultCallback<Void>?) {
        if (callback == null) {
            //ERROR!
        } else {
            try {
                googleSignInAccount = task.getResult(ApiException::class.java)
                callback.onResult(SimpleAuthResult.getSuccessResult(null))
            } catch (e: ApiException) {
                googleSignInAccount = null
                var errorResponse = AUTH_CLIENT_BASE_ERROR - e.statusCode
                var errorMsg = "GOOGLE_AUTH_ERROR_" + e.statusCode
                if (e.statusCode == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                    errorResponse = AUTH_CLIENT_USER_CANCELLED_ERROR
                    errorMsg = AUTH_CLIENT_USER_CANCELLED_ERROR_MESSAGE
                }

                callback.onResult(SimpleAuthResult.getFailResult(errorResponse, errorMsg))
            }

        }
    }

}