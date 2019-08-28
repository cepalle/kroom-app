package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.TrackVoteEventsViewModel
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackWithVote
import kotlinx.android.synthetic.main.fragment_music_track_vote_event_vote.*


class MusicTrackVoteVoteFragement(val eventId: Int) : Fragment() {
    private var adapterTrackVote: RecyclerViewAdapterMusicTrackVote? = null
    private var recyclerViewTrackVote: RecyclerView? = null
    private val trackVoteList: MutableList<TrackWithVote> = mutableListOf()
    lateinit var trackItem: TrackWithVote
    private var trackVoteEvent: TrackVoteEvent? = null
    lateinit var trackVoteViewModel: TrackVoteEventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Music tracks vote"
        val view: View? = inflater.inflate(R.layout.fragment_music_track_vote_event_vote, container, false)
        recyclerViewTrackVote = view?.findViewById(R.id.listTrackVoteEvent)

        if (adapterTrackVote != null) {
            if (trackVoteList != null) {
                adapterTrackVote!!.setTrackList(trackVoteList)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        music_track_vote_button_search_add_track.setOnClickListener {
            val musicTrackVoteSearchAddActivityIntent =
                Intent(activity, MusciTrackVoteSearchAddTrackActivity::class.java).apply {
                    putExtra(EXTRA_EVENT_ID, eventId)
                }
            startActivity(musicTrackVoteSearchAddActivityIntent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            trackVoteViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
        }

        recyclerViewTrackVote?.layoutManager = (context?.let { CustomLayoutManager(it) })
        recyclerViewTrackVote?.setHasFixedSize(true)

        trackVoteViewModel.getErrorMsg().observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        })

        adapterTrackVote = trackVoteList?.let {
            RecyclerViewAdapterMusicTrackVote(it) {
                OnTrackVoteSelected(it)
            }
        }
        recyclerViewTrackVote?.adapter = adapterTrackVote

        trackVoteViewModel.subTrackVoteByid(eventId).observe(viewLifecycleOwner, Observer {
            Toast.makeText(this.context, "SUB", Toast.LENGTH_SHORT).show()
            Log.e("SUB", it.toString())

            trackVoteEvent = it
            trackVoteList.clear()
            trackVoteEvent?.trackWithVote?.forEach {
                if (it != null) {
                    trackVoteList.add(it)
                }
            }
            //  eventsPublicViewModel.getSelectedTrackVoteEvent()?.postValue(eventItem)
            adapterTrackVote?.setTrackList(trackVoteList)
            adapterTrackVote?.notifyDataSetChanged()
        })
    }

    fun OnTrackVoteSelected(trackVoteItem: TrackWithVote) {



        trackItem = trackVoteItem
    }
}


