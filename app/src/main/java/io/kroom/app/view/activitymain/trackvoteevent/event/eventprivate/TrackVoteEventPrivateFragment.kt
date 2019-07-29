package io.kroom.app.view.activitymain.trackvoteevent.event.eventprivate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.event.eventpublic.RecyclerViewAdapterTrackEventPublic
import io.kroom.app.view.activitymain.trackvoteevent.event.TrackVoteEventsViewModel
import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.view.activitymain.trackvoteevent.musictrackvote.MusicTrackVoteActivity
import kotlinx.android.synthetic.main.fragment_list_public_events.*

class TrackVoteEventPrivateFragment : Fragment() {
    private var adapterTrackEventPrivate: RecyclerViewAdapterTrackEventPublic? = null
    private var recyclerViewEventsPrivate: RecyclerView? = null
    private var trackVoteEventListPrivate: List<EventModel> = listOf()
    lateinit var eventsPrivateViewModel: TrackVoteEventsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater.inflate(R.layout.fragment_list_public_events, container, false)

        recyclerViewEventsPrivate = view?.findViewById(R.id.listPublicEvents)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (adapterTrackEventPrivate != null && trackVoteEventListPrivate != null) {
            adapterTrackEventPrivate!!.setEventList(trackVoteEventListPrivate)
        }

        listPublicEvents.setLayoutManager(context?.let { CustomLayoutManager(it) })
        listPublicEvents.setHasFixedSize(true)
        listPublicEvents.adapter =
            RecyclerViewAdapterTrackEventPublic(
                trackVoteEventListPrivate,
                { eventItem: EventModel ->
                    OnsTrackVoteEventSelected(eventItem)
                })

        activity?.let {
            eventsPrivateViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
            eventsPrivateViewModel.getTrackVoteEventPrivateList().observe(viewLifecycleOwner, Observer {
                trackVoteEventListPrivate = it
                adapterTrackEventPrivate!!.setEventList(trackVoteEventListPrivate)

            })

            //    Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()

        }
    }

    fun OnsTrackVoteEventSelected(eventItem: EventModel) {

        eventsPrivateViewModel.getSelectedTrackVoteEventPrivate()?.postValue(eventItem)
        val musicTrackVoteActivityIntent = Intent(activity, MusicTrackVoteActivity::class.java)
        musicTrackVoteActivityIntent.putExtra(Intent.EXTRA_REFERRER_NAME, "MusicTrackVoteFragmentPrivate()")
        startActivity(musicTrackVoteActivityIntent)

    }
}

