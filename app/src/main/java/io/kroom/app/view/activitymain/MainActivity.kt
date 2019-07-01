package io.kroom.app.view.activitymain

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.kroom.app.R
import io.kroom.app.util.Session
import io.kroom.app.view.activitymain.playlist.PlaylistFragment
import io.kroom.app.view.activitymain.trackvoteevent.TrackVoteEventFragment
import io.kroom.app.view.activitymain.user.UserFriendsFragment
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import io.kroom.app.view.activityauth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*


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

        bottomNavigationView.setOnNavigationItemSelectedListener {
            it.itemId.toRoute()?.let(::goToRoute)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.head_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.LogoutAction -> {
                Toast.makeText(this, "Logout !!!", Toast.LENGTH_SHORT).show()

            }
        }

        return super.onOptionsItemSelected(item)
    }
    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.TRACK_VOTE_EVENT -> changeFragment(TrackVoteEventFragment())
            Routes.PLAYLIST_EDITOR -> changeFragment(PlaylistFragment())
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
