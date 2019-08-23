package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote.activitymusictrackvoteuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote.EXTRA_EVENT_ID

class MusicTrackVoteUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Event Vote User"
        setContentView(R.layout.activity_track_vote_event_user)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, 0)
    }
}
