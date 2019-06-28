package io.kroom.app.view.activitymain.playlisteditor.activityplaylisteditor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.playlisteditor.activityplaylisteditor.tabs.PlaylistOrderFragement
import io.kroom.app.view.activitymain.playlisteditor.activityplaylisteditor.tabs.PlaylistTracksFragement
import io.kroom.app.view.activitymain.playlisteditor.activityplaylisteditor.tabs.PlaylistUsersFragement
import io.kroom.app.view.activitymain.playlisteditor.tabs.EXTRA_NAME_PLAYLIST_ID
import kotlinx.android.synthetic.main.activity_playlist_editor.*

class PlaylistEditorActivity : AppCompatActivity() {

    var model: PlaylistEditorViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_editor)

        if (savedInstanceState == null) {
            changeFragment(PlaylistOrderFragement())
        }

        val playlistId: Int = intent.getIntExtra(EXTRA_NAME_PLAYLIST_ID, 0)

        model = ViewModelProviders.of(this).get(PlaylistEditorViewModel::class.java)

        model?.let {
            aeditor_tabs_playlist_navigation.setScrollPosition(it.tabPosition, 0.0f, true)
        }

        aeditor_tabs_playlist_navigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.toRoute()?.let(::goToRoute)
                model?.tabPosition = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                if (tab.position != model?.tabPosition) {
                    tab.position.toRoute()?.let(::goToRoute)
                    model?.tabPosition = tab.position
                }
            }
        })

    }

    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.ORDER -> changeFragment(PlaylistOrderFragement())
            Routes.TRACKS -> changeFragment(PlaylistTracksFragement())
            Routes.USERS -> changeFragment(PlaylistUsersFragement())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.aeditor_tab_navigation_container, fragment)
            .commit()
    }

    private enum class Routes(val id: Int) {
        ORDER(0),
        TRACKS(1),
        USERS(2);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}
