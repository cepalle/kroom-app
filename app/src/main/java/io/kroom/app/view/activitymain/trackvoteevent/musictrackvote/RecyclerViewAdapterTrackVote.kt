package io.kroom.app.view.activitymain.trackvoteevent.musictrackvote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.model.Track
import kotlinx.android.synthetic.main.row_item_music_track_vote.view.*
import kotlinx.android.synthetic.main.row_item_track_vote_event.view.*

class RecyclerViewAdapterTrackVote (
    private val trackVoteList: List<Track>,
    val onTrackVoteListener: (Track) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterTrackVote.TrackVoteHolder>() {

    private var _trackVoteList: List<Track> = trackVoteList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackVoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_music_track_vote, parent, false)
        return TrackVoteHolder(itemView)
    }

    override fun getItemCount() = trackVoteList.size

    fun setTrackList(tracVotetList: List<Track>) {
        _trackVoteList = trackVoteList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrackVoteHolder, position: Int) {

        (holder as TrackVoteHolder).bind(trackVoteList[position], onTrackVoteListener)
    }

    inner class TrackVoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            track: Track,
            onTrackVoteListener: (Track) -> Unit
        ) {
            itemView.itemMusicTrackVoteCoverSmall.drawable
            itemView.itemTrackVoteEventName.text = track.artist.name
            itemView.itemMusicTrackVoteTitle.text = track.title
            itemView.itemMusicTrackVoteDuration.text = track.duration.toString()

        }
    }
}