package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R


class MusciTrackVoteSearchAddTrackActivity(val eventId:Int) : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Search & Add Track"
        setContentView(R.layout.activity_music_track_vote_search_add)

    }
}