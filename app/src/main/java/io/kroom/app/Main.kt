package io.kroom.app

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

import io.kroom.app.views.HomeFragment
import io.kroom.app.views.UserSignInFragment
import io.kroom.app.views.UserSignUpFragment
import kotlinx.android.synthetic.main.activity_main.*


class Main : AppCompatActivity() {

    private var tokenTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = this

        setContentView(R.layout.activity_main)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        bottom_navigation.setOnNavigationItemReselectedListener {
            Routes.fromInt(it.itemId)?.let { route ->
                goToRoute(route)
            }
            tokenTextView = findViewById(R.id.tokenView) as TextView
        }

        if (savedInstanceState == null) {
            changeFragment(HomeFragment())
        }
    }

    fun goToRoute(route: Routes) {
        when (route) {
            Routes.HOME -> changeFragment(HomeFragment())
            Routes.MUSICS -> TODO()
            Routes.SETTINGS -> TODO()
            Routes.USER_SIGN_IN -> changeFragment(UserSignInFragment())
            Routes.USER_SIGN_UP -> changeFragment(UserSignUpFragment())
            Routes.USER_SIGN_IN_GOOGLE ->TODO()
            Routes.USER_SIGN_UP_GOOGLE -> TODO()

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

}

enum class Routes(val id: Int) {
    HOME(R.id.bottomNavigationHome),
    MUSICS(R.id.bottomNavigationMusics),
    SETTINGS(R.id.bottomNavigationSettings),

    USER_SIGN_IN(-1),
    USER_SIGN_UP(-2),
    USER_SIGN_IN_GOOGLE(-3),
    USER_SIGN_UP_GOOGLE(-4),

    ;

    companion object {
        fun fromInt(n: Int): Routes? {
            return values().find { it.id == n }
        }
    }
}