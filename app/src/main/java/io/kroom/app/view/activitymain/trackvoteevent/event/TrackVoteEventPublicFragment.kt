package io.kroom.app.view.activitymain.trackvoteevent.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R
import io.kroom.app.util.Session


class TrackVoteEventPublicFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Eventpubliclist Editor"
        return inflater.inflate(R.layout.fragment_track_vote_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
          //  changeFragment(PlaylistPublicFragment())
        }

        Session.getToken(this.activity?.application!!)?.let {
            Log.i("DEBUG", "IL SE PASSE QQCH")

         /*   trackVoteEventLogout.setOnClickListener {
                Log.i("DEBUG", "JE ME LOGOUT")
                Session.removeUser(this.activity?.application!!)
            }*/
         //   val te = "${Session.getEmail(this.activity?.application!!)!!} - ${Session.getId(this.activity?.application!!)!!}"
           // trackVoteEventDebug.text = te
        }
    }
}