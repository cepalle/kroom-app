package io.kroom.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import android.view.inputmethod.InputMethodManager
import com.apollographql.apollo.subscription.OperationClientMessage

import io.kroom.app.fragments.UserSignInFragment
import io.kroom.app.fragments.UserSignUpFragment
import io.kroom.app.fragments.HomeFragment
import io.kroom.app.fragments.StartFragment

import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.annotations.Nullable


class Main : AppCompatActivity() {

    private var fragmentStart:  StartFragment? = null
  //  private lateinit var bt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = this

        setContentView(R.layout.activity_main)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
      /*  fragmentStart = StartFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragmentStart!!, null).commit()*/
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
            Routes.START -> changeFragment(StartFragment())
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

    }

    override fun onStart() {
        super.onStart()
        getDelegate().onStart()
    }

    protected override fun onRestart() {
        super.onRestart()

    }

    protected override fun onResume() {
        super.onResume()
    }

    protected override fun onPause() {
        super.onPause()
    }

    protected override fun onStop() {
        super.onStop()
        getDelegate().onStop()

    }

    protected override fun onDestroy() {
        super.onDestroy()
        getDelegate().onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}

enum class Routes(val id: Int) {
    START(R.id.startConnexion),
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