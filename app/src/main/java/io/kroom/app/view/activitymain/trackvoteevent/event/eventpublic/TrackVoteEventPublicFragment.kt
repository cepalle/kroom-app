package io.kroom.app.view.activitymain.trackvoteevent.event.eventpublic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.event.RecyclerViewAdapterTrackEvent
import io.kroom.app.view.activitymain.trackvoteevent.event.TrackVoteEventsViewModel
import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import io.kroom.app.view.activitymain.trackvoteevent.musictrackvote.MusicTrackVoteFragment
import kotlinx.android.synthetic.main.fragment_list_public_events.*


class TrackVoteEventPublicFragment : Fragment() {
    private var adapterTrackEventPublic: RecyclerViewAdapterTrackEvent? = null
    private var recyclerViewEventsPublic: RecyclerView? = null
    private var trackVoteEventList: List<EventModel> = listOf()
    lateinit var eventsPublicViewModel: TrackVoteEventsViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = inflater.inflate(R.layout.fragment_list_public_events, container, false)

        recyclerViewEventsPublic = view?.findViewById(R.id.listPublicEvents)
        if (adapterTrackEventPublic != null && trackVoteEventList != null) {
            adapterTrackEventPublic!!.setEventList(trackVoteEventList)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        trackVoteEventList = createTestData()



        recyclerViewEventsPublic?.setLayoutManager(context?.let { CustomLayoutManager(it) })
        recyclerViewEventsPublic?.setHasFixedSize(true)
        recyclerViewEventsPublic?.adapter = RecyclerViewAdapterTrackEvent(
              trackVoteEventList,
              { eventItem: EventModel ->
                  OnsTrackVoteEventSelected( eventItem )

              })
       

            activity?.let {
                eventsPublicViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
                eventsPublicViewModel.getTrackVoteEventPublicList().observe(viewLifecycleOwner, Observer {
                   trackVoteEventList = it
                    adapterTrackEventPublic?.setEventList(trackVoteEventList)

                })

                //    Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()
            }
    }
    private fun createTestData() : List<EventModel> {
        val EventList = ArrayList<EventModel>()
        EventList.add(EventModel(100411, "toto", "titi", true, 8888888, 77777777, null, null))
        EventList.add(EventModel(101119, "toto", "titi", true, 8888888, 77777777, null, null))
        EventList.add(EventModel(101624, "toto", "titi", true, 8888888, 77777777, null, null))
        return EventList
    }

    fun OnsTrackVoteEventSelected(eventItem: EventModel) {
        eventsPublicViewModel.getSelectedTrackVoteEventPublic()?.postValue(eventItem)
        fragmentManager!!.beginTransaction().replace(R.id.container, MusicTrackVoteFragment())
            .addToBackStack(null)
            .commit()
    }

}