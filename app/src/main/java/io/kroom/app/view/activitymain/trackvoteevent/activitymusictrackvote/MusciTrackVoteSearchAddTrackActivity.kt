package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R
import io.kroom.app.util.Session


class MusciTrackVoteSearchAddTrackActivity : AppCompatActivity()  {

    var eventId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Search & Add Track"
        setContentView(R.layout.activity_music_track_vote_search_add)
        eventId = intent.getIntExtra(EXTRA_EVENT_ID, 0)



      /*  fun addOrUpdateMusicTrackVoteList(eventId: Int, userId: Int, musicId: Int, up: Boolean) {
            Session.getId(getApplication())!!.let {
                trackVoteEventRepo.trackVoteEventAddOrUpdateVote(eventId, userId, musicId, up).observe()(this, Observer {
                    it.onSuccess {
                        if (it.TrackVoteEventNew().errors().isEmpty()) {
                            Toast.makeText(context, "Event created", Toast.LENGTH_SHORT).show()
                            eventAddNameEdit.setText("")
                        } else {
                            eventAddNameEdit.error = it.TrackVoteEventNew().errors()[0].messages()[0]
                        }
                    }
                    it.onFailure {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }*/

    }
}