package io.kroom.app.idp

import android.app.Activity
import android.content.Intent
import com.jk.simple.SimpleAuthResultCallback
import com.jk.simple.idp.AuthClient
import com.jk.simple.idp.GoogleAuthClient
import com.jk.simple.idp.IdpType

abstract class AuthClient {


    var AUTH_CLIENT_BASE_ERROR = -100
    var AUTH_CLIENT_USER_CANCELLED_ERROR = -101
    var AUTH_CLIENT_LOGIN_ERROR = -102
    var AUTH_CLIENT_PROVIDER_ERROR = -110
    var AUTH_CLIENT_INIT_ERROR = -120

    var AUTH_CLIENT_USER_CANCELLED_ERROR_MESSAGE = "USER_CANCELLED"

    fun getAuthClientInstance(idpType: IdpType): AuthClient? {
        when (idpType) {
            IdpType.GOOGLE -> return GoogleAuthClient()
        }

        return null
    }

    abstract fun login(activity: Activity, callback: SimpleAuthResultCallback<Void>)

    abstract fun logout()

    abstract fun getToken(): String

    abstract fun getEmail(): String

    abstract fun getIdpType(): IdpType

    abstract fun isSignedIn(activity: Activity): Boolean

    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)


}