package io.kroom.app.view.activitymain.trackvoteevent.event

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.util.Session
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent
import kotlinx.android.synthetic.main.fragment_list_public_events.*


class TrackVoteEventPublicFragment : Fragment() {
    private var adapterTrackEventPublic: RecyclerViewAdapterTrackEvent? = null
    private var recyclerViewEventsPublic: RecyclerView? = null
    private var trackVoteEventList: List<TrackVoteEventsPublicQuery.TrackVoteEventsPublic> = listOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = inflater.inflate(R.layout.fragment_list_public_events, container, false)
        recyclerViewEventsPublic = view?.findViewById(R.id.listPublicEvents)


        if (adapterTrackEventPublic != null && trackVoteEventList != null) {
            adapterTrackEventPublic!!.setEventList(trackVoteEventList.map {

                RecyclerViewAdapterTrackEvent.EventModel(
                    it.id(),
                    it.userMaster()?.userName() ?: return@map null,
                    it.name(),
                    it.public_(),
                    0,
                    0,
                    it.latitude()?.toFloat() ?: return@map null,
                    it.longitude()?.toFloat() ?: return@map null
                )
            }.filterNotNull())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            //  changeFragment(PlaylistPublicFragment())
        }
        listPublicEvents.setLayoutManager(context?.let { CustomLayoutManager(it) })
        listPublicEvents.setHasFixedSize(true)


        //   listPublicEvents.adapter = RecyclerViewAdapterTrackEvent (trackVoteEventList,{eventItem: TrackVoteEvent -> })
        activity?.let {
            val eventsPublicViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
            eventsPublicViewModel.getTrackVoteEventPublicList().observe(this, Observer {
                it.onSuccess {
                    trackVoteEventList = it.TrackVoteEventsPublic()
                }
                it.onFailure {

                }

            })
            // getTrackVoteEventPublicList().observe(this, Observer { it?.let {
            //    Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()
        }
    }


}



