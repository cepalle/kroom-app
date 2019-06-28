package io.kroom.app.view.activitymain.trackvoteevent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R
import io.kroom.app.util.Session
import kotlinx.android.synthetic.main.fragment_track_vote_event.*


class TrackVoteEventFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Track Vote Event"
        return inflater.inflate(R.layout.fragment_track_vote_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("DEBUG", "HELLO WORLD")

        Session.getToken(this.activity?.application!!)?.let {
            Log.i("DEBUG", "IL SE PASSE QQCH")

            trackVoteEventLogout.setOnClickListener {
                Log.i("DEBUG", "JE ME LOGOUT")
                Session.removeUser(this.activity?.application!!)
            }
            val te = "${Session.getEmail(this.activity?.application!!)!!} - ${Session.getId(this.activity?.application!!)!!}"
            trackVoteEventDebug.text = te
        }
    }
}