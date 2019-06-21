package io.kroom.app.session

import android.content.Context
import io.kroom.app.view.main.MainActivity

object Session {

    var context: Context? = null

    fun init(c: Context) {
        // TODO delete on delete ?
        context = c
    }

    fun isConnected(): Boolean {

        val sharedPreferences = context!!.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null) != null
    }

    fun setUser(id: Int, email: String, username: String, token: String) {
        val sharedPref = context!!.getSharedPreferences("user", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("token", token)
            putString("email", email)
            putInt("id", id)
            putString("username", username)
            apply()
        }
    }

    fun removeUser() {
        val sharedPref = context!!.getSharedPreferences("user", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("token", null)
            putString("email", null)
            putString("id", null)
            putString("username", null)
            apply()
        }
    }

    fun getUsername(): String? {
        val sharedPreferences = context!!.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    fun getEmail(): String? {
        val sharedPreferences = context!!.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }

    fun getId(): Int? {
        val sharedPreferences = context!!.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("id", 0)
    }

    fun getToken(): String? {
        val sharedPreferences = context!!.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }
}