package io.kroom.app.view.activitymain.trackvoteevent.musictrackvote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.CustomLayoutManager
import io.kroom.app.view.activitymain.trackvoteevent.model.Track


class MusicTrackVotePublicFragment : Fragment() {


    private var adapterTrackVote: RecyclerViewAdapterTrackVote? = null
    private var recyclerViewTrackVote: RecyclerView? = null
    private var trackVoteList: List<Track> = listOf()
    lateinit var trackItem: Track


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Music tracks vote"
        val view: View? = inflater.inflate(R.layout.fragment_track_vote_event, container, false)
        recyclerViewTrackVote = view?.findViewById(R.id.list_track_vote)

        if (adapterTrackVote != null && trackVoteList != null) {
            adapterTrackVote!!.setTrackList(trackVoteList)

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
        recyclerViewTrackVote?.setLayoutManager(context?.let { CustomLayoutManager(it) })
        recyclerViewTrackVote?.setHasFixedSize(true)
        recyclerViewTrackVote?.adapter = RecyclerViewAdapterTrackVote(trackVoteList,
             { trackItem: Track -> OnTrackVoteSelected(trackItem) })
      /*   activity?.let {
             val trackVoteViewModel = ViewModelProviders.of(it).get(MusicTrackVoteViewModel::class.java)
             trackVoteViewModel.trackVoteEventPublicList().observe(viewLifecycleOwner, Observer {
                 trackVoteList = it
                 //  eventsPublicViewModel.getSelectedTrackVoteEvent()?.postValue(eventItem)
                 adapterTrackVote?.setTrackList(trackVoteList)
             })
             //    Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()
         }*/
     }
     fun OnTrackVoteSelected(trackVoteItem: Track) {
      //   trackItem = trackVoteItem
     }
}


