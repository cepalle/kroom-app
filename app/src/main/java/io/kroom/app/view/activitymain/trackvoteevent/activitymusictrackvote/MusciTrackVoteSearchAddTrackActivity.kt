package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.kroom.app.R
import io.kroom.app.repo.TrackVoteEventRepo
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient
import kotlinx.android.synthetic.main.activity_music_track_vote_search_add.*


class MusciTrackVoteSearchAddTrackActivity : AppCompatActivity()  {

    var eventId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Search & Add Track"
        setContentView(R.layout.activity_music_track_vote_search_add)
        eventId = intent.getIntExtra(EXTRA_EVENT_ID, 0)

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

        fun addOrUpdateMusicTrackVoteList(eventId: Int, userId: Int, musicId: Int, up: Boolean) {
            val client = GraphClient {
                this?.let {
                    Session.getToken(it.application)
                }

            }.client

            val trackVoteRepo = TrackVoteEventRepo(client)

            musicTrackVoteAddBtnTrack.setOnClickListener{
                val inputTrack = trackVoteSearchTrack.text

         /*   Session.getId(getApplication())!!.let {
                trackVoteRepo.trackVoteEventAddOrUpdateVote(eventId, userId, musicId, up).observe(this, Observer {
                    it.onSuccess {
                        if (it.TrackVoteEventAddOrUpdateVote().errors().isEmpty()) {
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
             }*/
            }

        }

    }
}