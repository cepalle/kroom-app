package io.kroom.app.view.activitymain.playlist.tabs.activityplaylisteditor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.playlist.tabs.activityplaylisteditor.tabs.PlaylistEditorOrderFragement
import io.kroom.app.view.activitymain.playlist.tabs.activityplaylisteditor.tabs.PlaylistEditorTracksFragement
import io.kroom.app.view.activitymain.playlist.tabs.activityplaylisteditor.tabs.PlaylistEditorUsersFragement
import io.kroom.app.view.activitymain.playlist.tabs.EXTRA_NAME_PLAYLIST_ID
import kotlinx.android.synthetic.main.activity_playlist_editor.*

class PlaylistEditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_editor)

        if (savedInstanceState == null) {
            changeFragment(PlaylistEditorOrderFragement())
        }

        val playlistId: Int = intent.getIntExtra(EXTRA_NAME_PLAYLIST_ID, 0)

        playlistEditorNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.toRoute()?.let(::goToRoute)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.ORDER -> changeFragment(PlaylistEditorOrderFragement())
            Routes.TRACKS -> changeFragment(PlaylistEditorTracksFragement())
            Routes.USERS -> changeFragment(PlaylistEditorUsersFragement())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.playlistEditorNavigationContainer, fragment)
            .commit()
    }

    private enum class Routes(val id: Int) {
        ORDER(0),
        TRACKS(1),
        USERS(2);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}
