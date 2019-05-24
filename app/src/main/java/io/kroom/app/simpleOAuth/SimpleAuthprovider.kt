package io.kroom.app.simpleOAuth

import com.jk.simple.idp.IdpType
import java.util.HashMap


class SimpleAuthprovider() {

    private var authMap: MutableMap<IdpType, String>? = HashMap()

    private var instance: SimpleAuthprovider? = null

    fun getInstance(): SimpleAuthprovider {
        if (instance == null) {
            instance = SimpleAuthprovider()
        }
        return instance as SimpleAuthprovider
    }

    fun getServerId(type: IdpType): String? {
        val getype = authMap?.get(type)
        return getype
    }

    fun addServerId(type: IdpType, serverId: String) {
        authMap?.set(type, serverId)
    }


}