package io.kroom.app.view.TMP.fragment_TOSORT

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R

class PlaylistEditorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Services"
        return inflater.inflate(R.layout.fragment_playlist_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // musicTrackVote.setOnClickListener {
        //     MainActivity.app.goToRoute(Routes.MUSIC_TRACK_VOTE)
        // }
        // musicControlDelegation.setOnClickListener {
        //     MainActivity.app.goToRoute(Routes.MUSIC_CONTROL_DELEGATION)
        // }
        // musicPlaylistEditor.setOnClickListener {
        //     MainActivity.app.goToRoute(Routes.MUSIC_PLAYLIST_EDITOR)
        // }

    }

}