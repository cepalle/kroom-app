package io.kroom.app.view.TMP.fragment_TOSORT

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R

class MusicPlayListPublicEventsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Playlist editor"
        return inflater.inflate(R.layout.fragment_list_public_events, container, false)
    }
}