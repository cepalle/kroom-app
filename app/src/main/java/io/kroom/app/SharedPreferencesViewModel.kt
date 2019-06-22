package io.kroom.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class SharedPreferencesViewModel(app: Application) : AndroidViewModel(app) {

    fun isConnected(): Boolean {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null) != null
    }

    fun setUser(id: Int, email: String, username: String, token: String) {
        val sharedPref = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("token", token)
            putString("email", email)
            putInt("id", id)
            putString("username", username)
            apply()
        }
    }

    fun removeUser() {
        val sharedPref = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("token", null)
            putString("email", null)
            putString("id", null)
            putString("username", null)
            apply()
        }
    }

    fun getUsername(): String? {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    fun getEmail(): String? {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }

    fun getId(): Int? {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("id", 0)
    }

    fun getToken(): String? {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }
}