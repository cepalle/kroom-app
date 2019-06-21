package io.kroom.app.view.main.musictrackvoteevents


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kroom.app.R
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.model.TrackVoteEvent

class RecyclerViewAdapterTrackEvent: RecyclerView.Adapter<RecyclerViewAdapterTrackEvent.TrackEventVoteViewHolder>() {

    private var clientEvent:TrackVoteEventsPublicQuery.TrackVoteEventsPublic? = null
    private lateinit var trackVoteEvent:List<TrackVoteEvent>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackEventVoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_track_vote_public_event, parent, false)
        return TrackEventVoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return trackVoteEvent.size
    }

    override fun onBindViewHolder(holder: TrackEventVoteViewHolder, position: Int) {
        val event = trackVoteEvent[position]

    }

    class  TrackEventVoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

  }
}