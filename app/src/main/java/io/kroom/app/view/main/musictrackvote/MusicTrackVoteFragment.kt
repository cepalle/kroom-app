package io.kroom.app.view.main.musictrackvote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R


class MusicTrackVoteFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Vote tracks"
        return inflater.inflate(R.layout.fragment_music_track_vote, container, false)
    }
}