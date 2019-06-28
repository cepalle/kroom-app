package io.kroom.app.view.activitymain

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.kroom.app.R
import io.kroom.app.util.Session
import io.kroom.app.view.activitymain.playlisteditor.PlaylistEditorFragment
import io.kroom.app.view.activitymain.trackvoteevent.TrackVoteEventFragment
import io.kroom.app.view.activitymain.user.UserFriendsFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import io.kroom.app.view.activityauth.AuthActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("TEST", "is connected: ${Session.isConnected(application)}")
        if (!Session.isConnected(application)) {
            ContextCompat.startActivity(this, Intent(this, AuthActivity::class.java), null)
        }

        if (savedInstanceState == null) {
            changeFragment(TrackVoteEventFragment())
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            it.itemId.toRoute()?.let(::goToRoute)
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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
            .commit()
    }

    private enum class Routes(val id: Int) {
        TRACK_VOTE_EVENT(R.id.bottomNavigationTrackVoteEvent),
        PLAYLIST_EDITOR(R.id.bottomNavigationPlayListEditor),
        USER(R.id.bottomNavigationUser);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}
