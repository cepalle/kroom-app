package io.kroom.app.view.activitymain.trackvoteevent.event


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent
import kotlinx.android.synthetic.main.row_item_track_vote_public_event.view.*

class RecyclerViewAdapterTrackEvent(
    private val trackVoteEventList: List<EventModel>,
    val onTrackVoteEventListener: (EventModel) -> Result<TrackVoteEventsPublicQuery.TrackVoteEventsPublic>
) : RecyclerView.Adapter<RecyclerViewAdapterTrackEvent.TrackEventVoteHolder>() {

    data class EventModel(
        val id: Int,
        val userMasterName: String,
        val name: String,
        val public: Boolean,
        val scheduleBegin: Long,
        val scheduleEnd: Long,
        val latitude: Float,
        val longitude: Float
    )

    private var _trackVoteEventList: List<EventModel> = trackVoteEventList

    // private lateinit var trackVoteEvent: List<EventModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackEventVoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_track_vote_public_event, parent, false)
        return TrackEventVoteHolder(itemView)
    }

    override fun getItemCount() = trackVoteEventList.size

    fun setEventList(tracVoteEventList: List<EventModel>) {
        _trackVoteEventList = tracVoteEventList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrackEventVoteHolder, position: Int) {

        (holder as TrackEventVoteHolder).bind(trackVoteEventList[position], onTrackVoteEventListener)
    }

    inner class TrackEventVoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            trackVoteEvent: EventModel,
            onTrackVoteEventListener: (EventModel) -> Result<TrackVoteEventsPublicQuery.TrackVoteEventsPublic>
        ) {
            itemView.itemTrackVoteEventUserMaster.text = trackVoteEvent.userMasterName
            itemView.itemTrackVoteEventName.text = trackVoteEvent.name
            itemView.itemTrackVoteEventSchedule.text = trackVoteEvent.scheduleBegin.toString()
            itemView.itemTrackVoteEventLocation.text =
                "long: " + trackVoteEvent.longitude + " lat: " + trackVoteEvent.latitude
        }
    }
}

