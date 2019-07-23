package io.kroom.app.view.activitymain.trackvoteevent.event.eventprivate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.util.Session
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.event.RecyclerViewAdapterTrackEvent
import io.kroom.app.view.activitymain.trackvoteevent.event.TrackVoteEventsViewModel
import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.view.activitymain.trackvoteevent.musictrackvote.MusicTrackVoteFragment
import kotlinx.android.synthetic.main.fragment_list_public_events.*

class TrackVoteEventPrivateFragment: Fragment() {
    private var adapterTrackEventPrivate: RecyclerViewAdapterTrackEvent? = null
    private var recyclerViewEventsPrivate: RecyclerView? = null
    private var trackVoteEventList: List<EventModel> = listOf()
    lateinit var eventItem: EventModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = inflater.inflate(R.layout.fragment_list_public_events, container, false)

        recyclerViewEventsPrivate = view?.findViewById(R.id.listPublicEvents)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (adapterTrackEventPrivate != null && trackVoteEventList != null) {
            adapterTrackEventPrivate!!.setEventList(trackVoteEventList)
        }

        listPublicEvents.setLayoutManager(context?.let { CustomLayoutManager(it) })
        listPublicEvents.setHasFixedSize(true)
        listPublicEvents.adapter = RecyclerViewAdapterTrackEvent(
            trackVoteEventList,
            { eventItem: EventModel ->
                OnsTrackVoteEventSelected(eventItem)
            })

        /* activity?.let {
              val eventsPrivateViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
              eventsPrivateViewModel.getTrackVoteEventPrivateList().observe(viewLifecycleOwner, Observer {
                  trackVoteEventList
                  /* if (eventItem != null)
                   {
                       eventsPublicViewModel.getSelectedTrackVoteEvent()?.postValue(eventItem)
                   }*/
                  adapterTrackEventPrivate?.setEventList(trackVoteEventList)

              })

              //    Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()
          }*/
    }

    fun OnsTrackVoteEventSelected(eventVoteItem: EventModel) {
        eventItem = eventVoteItem
        fragmentManager!!.beginTransaction().replace(R.id.fragment_container, MusicTrackVoteFragment())
            .addToBackStack(null)
            .commit()
    }

}