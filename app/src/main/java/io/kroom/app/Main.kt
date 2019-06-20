package io.kroom.app

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import android.widget.Toast
import io.kroom.app.fragment.*

import kotlinx.android.synthetic.main.activity_main.*

/*
    fun hideKeyboard() {
        this.currentFocus?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
*/
class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectivityManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (!(networkInfo != null && networkInfo.isConnected))
            Toast.makeText(baseContext, "it seems you are not connected to the Internet", Toast.LENGTH_LONG).show()

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        bottom_navigation.setOnNavigationItemSelectedListener {
            it.itemId.toRoute()?.let(::goToRoute)
            true
        }

        if (savedInstanceState == null) {
            changeFragment(HomeFragment())
        }
    }

    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.HOME -> changeFragment(HomeFragment())
            Routes.MUSICS -> changeFragment(ServicesChooserFragment())
            Routes.USER_SIGN_IN -> changeFragment(UserSignInFragment())
            Routes.USER_SIGN_UP -> changeFragment(UserSignUpFragment())
            Routes.USER_FORGOT_PASSWORD -> changeFragment(MissingPasswordFragment())
            Routes.MUSIC_CONTROL_DELEGATION -> changeFragment(MusicControlDelegationFragment())
            Routes.MUSIC_PLAYLIST_EDITOR -> changeFragment(MusicPlaylistEditorFragement())
            Routes.MUSIC_TRACK_VOTE -> changeFragment(MusicTrackVoteFragment())
            Routes.DEBUG -> changeFragment(DebugFragment())
            Routes.SETTINGS -> changeFragment(SettingsFragment())
            Routes.USER_LOGGED -> changeFragment(UserLoggedFragment())
            Routes.USER_FRIENDS -> changeFragment(UserFriendsFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}

enum class Routes(val id: Int) {
    HOME(R.id.bottomNavigationHome),
    MUSICS(R.id.bottomNavigationMusics),
    SETTINGS(R.id.bottomNavigationSettings),

    USER_SIGN_IN(-100),
    USER_SIGN_UP(-101),
    USER_FORGOT_PASSWORD(-102),
    USER_LOGGED(-103),
    USER_FRIENDS(-104),

    MUSIC_CONTROL_DELEGATION(-200),
    MUSIC_PLAYLIST_EDITOR(-201),
    MUSIC_TRACK_VOTE(-202),

    DEBUG(-1000),
    ;
}

fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
