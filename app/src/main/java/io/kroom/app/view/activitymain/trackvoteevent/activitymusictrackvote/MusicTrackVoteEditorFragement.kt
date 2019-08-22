package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import kotlinx.android.synthetic.main.fragment_music_track_vote_editor.*

class MusicTrackVoteEditorFragement(private val eventId: Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_track_vote_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = activity?.let {
            ViewModelProviders.of(it).get(MusicTrackVoteEditorViewModel::class.java)
        }

        model ?: return

        musicTrackVoteEventEditorButtonDel.setOnClickListener {
            model.delTrackVote(eventId)
        }

        musicTrackVoteEventEditorButtonUser.setOnClickListener {
            val intent = Intent(activity, MusicTrackVoteActivity::class.java).apply {
                putExtra(EXTRA_EVENT_ID, eventId)
            }
            startActivity(intent)
        }

        musicTrackVoteEventEditorButtonUpdate.setOnClickListener {
            // TODO
        }

    }

}
