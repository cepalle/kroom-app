package io.kroom.app.view.activitymain

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.kroom.app.R
import io.kroom.app.util.SharedPreferences
import io.kroom.app.TMP.fragment_TOSORT.*
import io.kroom.app.view.activitymain.playlisteditor.PlaylistEditorFragment
import io.kroom.app.view.activitymain.trackvoteevent.TrackVoteEventFragment
import io.kroom.app.view.activitymain.user.UserFriendsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SharedPreferences.isConnected(application)) {
            // TODO launch activity SignIn or SignUp
        }

        if (savedInstanceState == null) {
            changeFragment(TrackVoteEventFragment())
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            it.itemId.toRoute()?.let(::goToRoute)
            true
        }
    }

    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.TRACK_VOTE_EVENT -> changeFragment(TrackVoteEventFragment())
            Routes.PLAYLIST_EDITOR -> changeFragment(PlaylistEditorFragment())
            Routes.USER -> changeFragment(UserFriendsFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private enum class Routes(val id: Int) {
        TRACK_VOTE_EVENT(R.id.bottomNavigationTrackVoteEvent),
        PLAYLIST_EDITOR(R.id.bottomNavigationPlayListEditor),
        USER(R.id.bottomNavigationUser);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}
