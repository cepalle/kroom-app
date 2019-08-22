package io.kroom.app.view.activitymain.trackvoteevent.event.eventpublic

import android.content.Intent
import android.content.Intent.EXTRA_REFERRER_NAME
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.util.Session
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.TrackVoteEventsViewModel
import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.view.activitymain.trackvoteevent.musictrackvote.MusicTrackVoteActivity

class TrackVoteEventPublicFragment : Fragment() {
    private var adapterTrackEventPublicPublic: RecyclerViewAdapterTrackEventPublic? = null
    private var recyclerViewEventsPublic: RecyclerView? = null
    private var trackVoteEventList: List<EventModel> = listOf()
    lateinit var eventsPublicViewModel: TrackVoteEventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater.inflate(R.layout.fragment_list_public_events, container, false)

        recyclerViewEventsPublic = view?.findViewById(R.id.listPublicEvents)
        adapterTrackEventPublicPublic?.setEventList(trackVoteEventList)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Log.i("tokennnnnnnnnnnnnnnnn2", Session.getToken(activity!!.application).toString())

        recyclerViewEventsPublic?.layoutManager = context?.let { CustomLayoutManager(it) }
        recyclerViewEventsPublic?.setHasFixedSize(true)
        adapterTrackEventPublicPublic = RecyclerViewAdapterTrackEventPublic(trackVoteEventList) {
            onsTrackVoteEventSelected(it)
        }
        recyclerViewEventsPublic?.adapter = adapterTrackEventPublicPublic

        activity?.let {
            eventsPublicViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
            eventsPublicViewModel.getTrackVoteEventPublicList().observe(this, Observer {
                trackVoteEventList = it
                adapterTrackEventPublicPublic?.setEventList(trackVoteEventList)
                adapterTrackEventPublicPublic?.notifyDataSetChanged()
                // Toast.makeText(this.context, it.size.toString(), Toast.LENGTH_LONG).show()
            })
        }
    }

    fun onsTrackVoteEventSelected(eventItem: EventModel) {
        eventsPublicViewModel.getSelectedTrackVoteEventPublic()?.postValue(eventItem)

        val musicTrackVoteActivityIntent = Intent(activity, MusicTrackVoteActivity::class.java).apply {
            putExtra(EXTRA_REFERRER_NAME, "MusicTrackVotePublicFragment()")
        }
        startActivity(musicTrackVoteActivityIntent)
    }
}
