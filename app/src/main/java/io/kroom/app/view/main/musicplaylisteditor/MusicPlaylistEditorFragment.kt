package io.kroom.app.view.main.musicplaylisteditor

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kroom.app.R

class MusicPlaylistEditorFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Playlist editor"
        return inflater.inflate(R.layout.fragment_music_playlist_editor, container, false)
    }
}