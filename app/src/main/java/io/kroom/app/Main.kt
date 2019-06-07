package io.kroom.app

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import io.kroom.app.fragments.UserSignInFragment
import io.kroom.app.fragments.UserSignUpFragment
import io.kroom.app.fragments.HomeFragment
import io.kroom.app.fragments.StartFragment

import kotlinx.android.synthetic.main.activity_main.*


class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = this
        setContentView(R.layout.activity_main)

        val connectivityManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (!(networkInfo != null && networkInfo.isConnected))
            Toast.makeText(baseContext, "it seems you are not connected to the Internet", Toast.LENGTH_LONG).show()

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        bottom_navigation.setOnNavigationItemReselectedListener {
            Routes.fromInt(it.itemId)?.let { route ->
                goToRoute(route)
            }

        }

        if (savedInstanceState == null) {
            changeFragment(StartFragment())
        }
    }

    fun goToRoute(route: Routes) {
        when (route) {
            Routes.HOME -> changeFragment(HomeFragment())
            Routes.MUSICS -> TODO()
            Routes.SETTINGS -> TODO()
            Routes.USER_SIGN_IN -> changeFragment(UserSignInFragment())
            Routes.USER_SIGN_UP -> changeFragment(UserSignUpFragment())
        }
    }


    fun hideKeyboard() {
        this.currentFocus?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    companion object {
        lateinit var app: Main
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun onStart() {
        super.onStart()
        delegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        delegate.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }
}

enum class Routes(val id: Int) {
    HOME(R.id.bottomNavigationHome),
    MUSICS(R.id.bottomNavigationMusics),
    SETTINGS(R.id.bottomNavigationSettings),

    USER_SIGN_IN(-1),
    USER_SIGN_UP(-2);

    companion object {
        fun fromInt(n: Int): Routes? {
            return values().find { it.id == n }
        }
    }

}