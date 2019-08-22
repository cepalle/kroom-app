package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.TrackVoteEventsViewModel
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackModel
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackWithVote


class MusicTrackVoteVoteFragement(val eventId:Int) : Fragment() {
  /*  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_track_vote_vote, container, false)
    }*/
  private var adapterTrackVote: RecyclerViewAdapterMusicTrackVote? = null
    private var recyclerViewTrackVote: RecyclerView? = null
    private var trackVoteList: List<TrackWithVote> = listOf()
    lateinit var trackItem: TrackModel
    private var trackVoteEvent: TrackVoteEvent? = null
    lateinit var trackVoteViewModel: TrackVoteEventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Music tracks vote"
        val view: View? = inflater.inflate(R.layout.fragment_music_track_vote_event_public, container, false)
        recyclerViewTrackVote = view?.findViewById(R.id.musicTrackVoteList)

        if (adapterTrackVote != null) {
            adapterTrackVote!!.setTrackList(trackVoteList)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewTrackVote?.setLayoutManager(context?.let { CustomLayoutManager(it) })
        recyclerViewTrackVote?.setHasFixedSize(true)
        adapterTrackVote = RecyclerViewAdapterMusicTrackVote(trackVoteList,
            { trackItem: TrackModel -> OnTrackVoteSelected(trackItem) })
        recyclerViewTrackVote?.setAdapter(adapterTrackVote)

          activity?.let {
               trackVoteViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
                trackVoteViewModel.getTrackVoteEventById(eventId).observe(viewLifecycleOwner, Observer {
                    it.let {
                        trackVoteEvent  = it

                        trackVoteList = trackVoteEvent?.trackWithVote as List<TrackWithVote>

                           /* .let { TrackModel(
                            it.id()
                        ) }*/
                    }
                    //  eventsPublicViewModel.getSelectedTrackVoteEvent()?.postValue(eventItem)
                    adapterTrackVote?.setTrackList(trackVoteList)
                })
                //    Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()
            }
    }
    fun OnTrackVoteSelected(trackVoteItem: TrackModel) {
        trackItem = trackVoteItem
    }
}
