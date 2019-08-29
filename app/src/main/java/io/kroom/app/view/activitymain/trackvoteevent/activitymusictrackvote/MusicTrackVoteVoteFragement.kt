package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
    private var trackItem: TrackWithVote? = null
    private var trackVoteEvent: TrackVoteEvent? = null
    private var trackVoteViewModel: TrackVoteEventsViewModel? = null
    private var trackPlayer: TrackPlayer? = null
    var idCurrent: Int = 0
    var scCurent: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Music tracks vote"
        val view: View? = inflater.inflate(R.layout.fragment_music_track_vote_event_vote, container, false)
        recyclerViewTrackVote = view?.findViewById(R.id.listTrackVoteEvent)

        adapterTrackVote?.setTrackList(trackVoteList)

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

        activity?.let {
            trackVoteViewModel = ViewModelProviders.of(it).get(TrackVoteEventsViewModel::class.java)
        }

        trackPlayer = trackVoteViewModel?.makePlayer()

        recyclerViewTrackVote?.layoutManager = (context?.let { CustomLayoutManager(it) })
        recyclerViewTrackVote?.setHasFixedSize(true)

        trackVoteViewModel?.getErrorMsg()?.observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        })

        adapterTrackVote = trackVoteList.let {
            this.context?.let { it1 ->
                RecyclerViewAdapterMusicTrackVote(it1, it) {
                    trackVoteViewModel?.getTrackVoteEventAddOrUpdateVote(eventId, it.track.id, true)
                }
            }
        }

        recyclerViewTrackVote?.adapter = adapterTrackVote

        trackVoteViewModel?.subTrackVoteByid(eventId)?.observe(viewLifecycleOwner, Observer {
            // Toast.makeText(this.context, "SUB", Toast.LENGTH_SHORT).show()
            // Log.e("SUB", it.toString())
            trackVoteEvent = it
            val coverMedium = trackVoteEvent?.currentTrack?.coverMedium
            if (it?.currentTrack?.coverMedium != null) {
                val imageResource = this.context?.getResources()?.getIdentifier(coverMedium, null, "io.kroom.app")
                if (context != null && imageResource != null) {
                    val res = ContextCompat.getDrawable(context!!, imageResource)
                    musicTrackVoteCoverMedium.setImageDrawable(res)
                }
            }

            trackVoteList.clear()
            trackVoteEvent?.trackWithVote?.sortedBy { it?.score }?.reversed()?.forEach {
                if (it != null) {
                    trackVoteList.add(it)

                    trackVoteList.let { r ->
                        val scor = it.score
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

}
