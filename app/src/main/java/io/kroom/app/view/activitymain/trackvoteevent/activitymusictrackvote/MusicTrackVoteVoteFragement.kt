package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import androidx.recyclerview.widget.RecyclerView
import com.deezer.sdk.player.TrackPlayer
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker
import io.kroom.app.view.activitymain.MainActivity
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
    private lateinit var trackPlayer: TrackPlayer
    var idCurrent: Int = 0
    var scCurent: Int = 0

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
      //  trackPlayer = TrackPlayer(Application(), MainActivity.deezerConnect, WifiAndMobileNetworkStateChecker())

        activity?.let {
            trackVoteViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
        }

        recyclerViewTrackVote?.layoutManager = (context?.let { CustomLayoutManager(it) })
        recyclerViewTrackVote?.setHasFixedSize(true)

        trackVoteViewModel.getErrorMsg().observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        })

        adapterTrackVote = trackVoteList?.let {
            this.context?.let { it1 ->
                RecyclerViewAdapterMusicTrackVote(it1, it) {
                    OnTrackVoteSelected(it)
                }
            }
        }
        recyclerViewTrackVote?.adapter = adapterTrackVote

        trackVoteViewModel.subTrackVoteByid(eventId).observe(viewLifecycleOwner, Observer {
            // Toast.makeText(this.context, "SUB", Toast.LENGTH_SHORT).show()
            // Log.e("SUB", it.toString())
            trackVoteEvent = it
            val coverMedium = trackVoteEvent.let {
                it?.currentTrack?.coverMedium
            }
            if (it?.currentTrack?.coverMedium != null) {
                val imageResource = this.context!!.getResources()!!.getIdentifier(coverMedium, null, "io.kroom.app")
                val res = ContextCompat.getDrawable(context!!, imageResource)
                musicTrackVoteCoverMedium.setImageDrawable(res)
            }

            trackVoteList.clear()
            trackVoteEvent?.trackWithVote?.forEach {
                if (it != null) {
                    trackVoteList.add(it)

                    trackVoteList.let { r ->
                        var scor = it.score
                        if (scor > scCurent) {
                            scCurent = scor
                            idCurrent = it.track.id
                        }
                    }
                }
            }
           // trackPlayer.playTrack(idCurrent.toLong())
            //  eventsPublicViewModel.getSelectedTrackVoteEvent()?.postValue(eventItem)
            adapterTrackVote?.setTrackList(trackVoteList)
            adapterTrackVote?.notifyDataSetChanged()
        })
    }

    fun OnTrackVoteSelected(trackVoteItem: TrackWithVote) {
        trackItem = trackVoteItem
        /*   if ( trackVoteItem.nb_vote_down == true)
       {}*/
    }
}


