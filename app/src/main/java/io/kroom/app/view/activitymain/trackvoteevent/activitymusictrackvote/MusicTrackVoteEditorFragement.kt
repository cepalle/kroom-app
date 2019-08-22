package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R

class MusicTrackVoteEditorFragement(val eventId:Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_track_vote_editor, container, false)
    }

}
