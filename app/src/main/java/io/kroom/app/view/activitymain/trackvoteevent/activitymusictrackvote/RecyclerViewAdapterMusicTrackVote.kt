package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackWithVote
import kotlinx.android.synthetic.main.row_item_music_track_vote.view.*

class RecyclerViewAdapterMusicTrackVote(
    val trackVoteList:  MutableList<TrackWithVote>,
    val onTrackVoteListener: (TrackWithVote) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterMusicTrackVote.TrackVoteHolder>() {

    private var _trackVoteList:  MutableList<TrackWithVote> = trackVoteList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackVoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_music_track_vote, parent, false)
        return TrackVoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return _trackVoteList.size
    }

    fun setTrackList(tracVotetList:  MutableList<TrackWithVote>) {
        _trackVoteList = tracVotetList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrackVoteHolder, position: Int) {

        holder.bind(_trackVoteList[position])
        holder.clickableItem1.setOnClickListener{
            onTrackVoteListener(_trackVoteList[position])
        }
        holder.clickableItem2.setOnClickListener{
            onTrackVoteListener(_trackVoteList[position])
        }
    }

     class TrackVoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clickableItem1 = itemView.linearlayout1
        var clickableItem2 = itemView.linearlayout2
        fun bind(trackWithVote: TrackWithVote) {
            // itemView.itemMusicTrackVoteCoverSmall.drawable = trackWithVote.track.coverSmall
            itemView.itemMusicTrackVoteArtist.text = trackWithVote.track.artist
            itemView.itemMusicTrackVoteUp.text = trackWithVote.nb_vote_up.toString()
            itemView.itemMusicTrackVoteDown.text = trackWithVote.nb_vote_down.toString()
            itemView.itemMusicTrackVoteTitle.text = trackWithVote.track.title
            itemView.itemMusicTrackVoteDuration.text = trackWithVote.track.duration.toString()

        }
    }
}
