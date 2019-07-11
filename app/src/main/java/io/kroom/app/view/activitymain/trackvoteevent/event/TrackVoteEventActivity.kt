package io.kroom.app.view.activitymain.trackvoteevent.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.event.add.TrackVoteEventAddFragment
import kotlinx.android.synthetic.main.activity_music_track_vote_event_editor.*

class TrackVoteEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_track_vote_event_editor)

        if (savedInstanceState == null) {
            changeFragment(TrackVoteEventPublicFragment())
        }


        trackVoteEditorNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.toRoute()?.let(::goToRoute)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.PUBLIC -> changeFragment(TrackVoteEventPublicFragment())
            Routes.PRIVATE-> changeFragment(TrackVoteEventPrivateFragment())
            Routes.ADD -> changeFragment(TrackVoteEventAddFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.trackVoteEditorNavigationContainer, fragment)
            .commit()
    }

    private enum class Routes(val id: Int) {
        PUBLIC(0),
        PRIVATE(1),
        ADD(2);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}