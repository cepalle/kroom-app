package io.kroom.app.oauth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.jk.simple.SimpleAuthResult
import com.jk.simple.SimpleAuthResultCallback
import com.jk.simple.SimpleAuthprovider
import com.jk.simple.idp.AuthClient
import com.jk.simple.idp.IdpType

class SimpleSession {
    private val activity: Activity? = null

    init {
        currentClient = null
    }

    companion object {
        private val TAG = "[SimpleSession]"

        private val SDK_BASE_ERROR = -500
        private val SDK_ACTIVITY_NULL_ERROR = -501
        private val SDK_IDP_NULL_ERROR = -502

        private var currentClient: AuthClient? = null

        fun setAuthProvider(idpType: IdpType, serverId: String) {
            if (idpType != null && serverId != null)
                SimpleAuthprovider.getInstance().addServerId(idpType, serverId)
        }

        fun login(activity: Activity, idpType: IdpType, callback: SimpleAuthResultCallback<Void>) {
            if (activity == null) {
                callback.onResult(SimpleAuthResult.getFailResult(SDK_ACTIVITY_NULL_ERROR, "ACTIVITY_NULL"))

            } else if (idpType == null) {
                callback.onResult(SimpleAuthResult.getFailResult(SDK_IDP_NULL_ERROR, "IDP_TYPE_NULL"))

            } else if (callback == null) {
                Log.d(TAG, "Login Callback is null")

            } else {

                val authClient = AuthClient.getAuthClientInstance(idpType)

                SimpleSession.currentClient = authClient

                authClient.login(activity) { result ->
                    if (!result.isSuccess) {
                        SimpleSession.currentClient = null
                    }

                    callback.onResult(result)
                }
            }
        }

        fun logout() {
            if (currentClient != null) {
                currentClient!!.logout()

                currentClient = null
            }
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            if (currentClient != null) {
                currentClient!!.onActivityResult(requestCode, resultCode, data)
            }
        }

        fun isSignedIn(activity: Activity): Boolean {
            if (activity == null) {
                return false
            } else if (currentClient != null) {
                return currentClient!!.isSignedIn(activity)
            }
            return false
        }

        val currentIdpType: IdpType?
            get() = if (currentClient != null) currentClient!!.idpType else null

        val accessToken: String
            get() = if (currentClient != null) currentClient!!.token else ""

        val email: String
            get() = if (currentClient != null) currentClient!!.email else ""
    }

}