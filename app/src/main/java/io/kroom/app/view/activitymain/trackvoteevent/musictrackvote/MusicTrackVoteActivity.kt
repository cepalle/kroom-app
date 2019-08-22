package io.kroom.app.view.activitymain.trackvoteevent.musictrackvote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.event.eventpublic.TrackVoteEventPublicFragment
import kotlinx.android.synthetic.main.activity_music_track_vote_event_editor.*

const val EXTRA_EVENT_ID = "EXTRA_EVENT_ID"

class MusicTrackVoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_track_vote_event_editor)

        val eventId: Int = intent.getIntExtra(EXTRA_EVENT_ID, 0)

        textView42.text = eventId.toString()
    }
}
