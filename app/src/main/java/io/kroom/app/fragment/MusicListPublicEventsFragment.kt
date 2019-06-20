package io.kroom.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kroom.app.R

class MusicListPublicEventsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Playlist editor"
        return inflater.inflate(R.layout.fragment_list_public_events, container, false)
    }
}