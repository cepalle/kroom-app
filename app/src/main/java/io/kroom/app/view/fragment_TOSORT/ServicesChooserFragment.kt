package io.kroom.app.view.fragment_TOSORT

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kroom.app.view.main.MainActivity
import io.kroom.app.R
import io.kroom.app.view.main.Routes

class ServicesChooserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Services"
        return inflater.inflate(R.layout.fragment_services_chooser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicTrackVote.setOnClickListener {
            MainActivity.app.goToRoute(Routes.MUSIC_TRACK_VOTE)
        }
        musicControlDelegation.setOnClickListener {
            MainActivity.app.goToRoute(Routes.MUSIC_CONTROL_DELEGATION)
        }
        musicPlaylistEditor.setOnClickListener {
            MainActivity.app.goToRoute(Routes.MUSIC_PLAYLIST_EDITOR)
        }


    }

}