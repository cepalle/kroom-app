package io.kroom.app.view.activitymain.trackvoteevent.musictrackvote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.event.eventadd.TrackVoteEventAddFragment
import io.kroom.app.view.activitymain.trackvoteevent.event.eventprivate.TrackVoteEventPrivateFragment
import io.kroom.app.view.activitymain.trackvoteevent.event.eventpublic.TrackVoteEventPublicFragment
import kotlinx.android.synthetic.main.activity_music_track_vote_event_editor.*

class MusicTrackVoteActivity : AppCompatActivity() {

    private var fragmentSelected: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_track_vote_event_editor)



        var intentStartedThisActivity = getIntent()

        if (intentStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            fragmentSelected = intentStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT)
        }

        if (savedInstanceState == null) {
            changeFragment(convToNameClass(fragmentSelected))
        }

        musicTrackVoteEditorNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    1 -> changeFragment(MusicTrackVotePrivateFragment())
                    2 -> changeFragment(MusicTrackVoteAddFragment())
                    else -> changeFragment(MusicTrackVotePublicFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun convToNameClass(fragment: String): Fragment {
        var fragmentselect: Fragment = TrackVoteEventPublicFragment()
        when (fragment) {
            "MusicTrackVoteEventPublicFragment()" -> fragmentselect = MusicTrackVotePublicFragment()
            "MusicTrackVoteEventPrivateFragment()" -> fragmentselect = MusicTrackVotePrivateFragment()
            "MusicTrackVoteEventAddFragment()" -> fragmentselect = MusicTrackVoteAddFragment()

        }
        return fragmentselect
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.trackVoteEditorNavigationContainer, fragment)
            .commit()
    }

}
