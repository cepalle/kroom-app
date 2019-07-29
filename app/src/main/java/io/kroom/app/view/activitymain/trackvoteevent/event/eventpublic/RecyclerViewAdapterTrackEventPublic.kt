package io.kroom.app.view.activitymain.trackvoteevent.event.eventpublic


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import kotlinx.android.synthetic.main.row_item_track_vote_event.view.*

class RecyclerViewAdapterTrackEventPublic(
    val trackVoteEventList: List<EventModel>,
    val onTrackVoteEventListener: (EventModel) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterTrackEventPublic.TrackEventVoteHolder>() {


    private var _trackVoteEventList: List<EventModel> = trackVoteEventList

    // private lateinit var trackVoteEvent: List<EventModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackEventVoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_track_vote_event, parent, false)
        return TrackEventVoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return trackVoteEventList.size
    }

    fun setEventList(tracVoteEventList: List<EventModel>) {
        _trackVoteEventList = tracVoteEventList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrackEventVoteHolder, position: Int) {

        (holder as TrackEventVoteHolder).bind(_trackVoteEventList[position])
        (holder as TrackEventVoteHolder).clickableItem.setOnClickListener { onTrackVoteEventListener(_trackVoteEventList[position]) }

    }

    class TrackEventVoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clickableItem = itemView.linearlayoutEvent
        fun bind(trackVoteEvent: EventModel) {
            itemView.itemTrackVoteEventUserMaster.text = trackVoteEvent.userMasterName
            itemView.itemTrackVoteEventName.text = trackVoteEvent.name
            itemView.itemTrackVoteEventSchedule.text = trackVoteEvent.scheduleBegin.toString()
            itemView.itemTrackVoteEventLocation.text =
                "long: " + trackVoteEvent.longitude.toString() + " lat: " + trackVoteEvent.latitude.toString()

        }
    }
}