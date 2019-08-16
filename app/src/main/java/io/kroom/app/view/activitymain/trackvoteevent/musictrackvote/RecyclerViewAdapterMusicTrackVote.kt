package io.kroom.app.view.activitymain.trackvoteevent.musictrackvote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackModel
import kotlinx.android.synthetic.main.row_item_music_track_vote.view.*
import kotlinx.android.synthetic.main.row_item_track_vote_event.view.*

class RecyclerViewAdapterMusicTrackVote(
    val trackVoteList: List<TrackModel>,
    val onTrackVoteListener: (TrackModel) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterMusicTrackVote.TrackVoteHolder>() {

    private var _trackVoteList: List<TrackModel> = trackVoteList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackVoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_music_track_vote, parent, false)
        return TrackVoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return _trackVoteList.size
    }

    fun setTrackList(tracVotetList: List<TrackModel>) {
        _trackVoteList = trackVoteList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrackVoteHolder, position: Int) {

        holder.bind(_trackVoteList[position])
    }

    inner class TrackVoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var clickableItem = itemView.
        fun bind(track: TrackModel) {
            itemView.itemMusicTrackVoteCoverSmall.drawable
            itemView.itemTrackVoteEventName.text = track.artist
            itemView.itemMusicTrackVoteTitle.text = track.title
            itemView.itemMusicTrackVoteDuration.text = track.duration.toString()

        }
    }
}