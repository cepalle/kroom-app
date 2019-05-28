package io.kroom.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


import com.jk.simple.*
import com.jk.simple.idp.IdpType
import com.jk.simple.SimpleSession



import io.kroom.app.fragments.UserSignInFragment
import io.kroom.app.fragments.UserSignUpFragment
import kotlinx.android.synthetic.main.activity_main.*

import io.kroom.app.fragments.HomeFragment
import kotlinx.android.synthetic.main.fragment_home.*


import org.jetbrains.annotations.Nullable


class Main : AppCompatActivity() {

    private var tokenTextView: TextView? = null
    private lateinit var bt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = this

        setContentView(R.layout.activity_main)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        tokenTextView = findViewById(R.id.tokenView)
        SimpleSession.setAuthProvider(IdpType.GOOGLE,  "795222071121-p1fbtb3mv3cjo9priggduc335rd8ng4d.apps.googleusercontent.com")

        bottom_navigation.setOnNavigationItemReselectedListener {
            Routes.fromInt(it.itemId)?.let { route ->
                goToRoute(route)
            }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }

    private fun onLoginCallback(result: SimpleAuthResult<Void>) {
        val builder = StringBuilder()
        builder.append(if (result.isSuccess) SimpleSession.getCurrentIdpType().name + " Login is succeed" else "FAIL / " + result.errorCode + " / " + result.errorMessaage)
        tokenTextView?.setText(builder.toString())
    }

    fun onClick(v: View) {
       bt = v as Button
        when (bt.id) {

            R.id.homeGoogleLogin -> {
                SimpleSession.login(
                    this, IdpType.GOOGLE
                ) { result -> onLoginCallback(result) }
            }

            R.id.homeGoogleLogout -> {
                SimpleSession.logout()
                tokenTextView?.setText("Logout!")
                Toast.makeText(this, "Logout!", Toast.LENGTH_LONG ).show()
            }

            R.id.homeLogedIn -> {
                val builder = StringBuilder()
                builder.append(if (SimpleSession.isSignedIn(this)) "Yes! Idp Type Is " + SimpleSession.getCurrentIdpType() else "No..")
                tokenTextView?.setText(builder.toString())
            }

            R.id.homeIdpType -> {
                val idpType = SimpleSession.getCurrentIdpType()
                this.tokenTextView?.setText(idpType?.name ?: "Null")
            }

            R.id.homeAccessToken -> {
                this.tokenTextView?.setText(SimpleSession.getAccessToken())
            }
            R.id.homeEmail -> {
                this.tokenTextView?.setText(SimpleSession.getEmail())

            }

        }
    }
}

enum class Routes(val id: Int) {
    HOME(R.id.bottomNavigationHome),
    MUSICS(R.id.bottomNavigationMusics),
    SETTINGS(R.id.bottomNavigationSettings),

    USER_SIGN_IN(-1),
    USER_SIGN_UP(-2),



    ;

    companion object {
        fun fromInt(n: Int): Routes? {
            return values().find { it.id == n }
        }
    }


}