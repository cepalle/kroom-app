package io.kroom.app.view.activitymain.trackvoteevent.event


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.model.EventModel
import kotlinx.android.synthetic.main.row_item_track_vote_event.view.*

class RecyclerViewAdapterTrackEvent(
    val trackVoteEventList: List<EventModel>,
    val onTrackVoteEventListener: (EventModel) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterTrackEvent.TrackEventVoteHolder>() {


    private var _trackVoteEventList: List<EventModel> = trackVoteEventList

    // private lateinit var trackVoteEvent: List<EventModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackEventVoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_track_vote_event, parent, false)
        return TrackEventVoteHolder(itemView)
    }

    override fun getItemCount() : Int {
        return trackVoteEventList.size
    }

    fun setEventList(tracVoteEventList: List<EventModel>) {
        _trackVoteEventList = tracVoteEventList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrackEventVoteHolder, position: Int) {

        (holder as TrackEventVoteHolder).bind(trackVoteEventList[position], onTrackVoteEventListener)
    }

     class TrackEventVoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            trackVoteEvent: EventModel,
            onTrackVoteEventListener: (EventModel) -> Unit
        ) {
            itemView.itemTrackVoteEventUserMaster.text = trackVoteEvent.userMasterName
            itemView.itemTrackVoteEventName.text = trackVoteEvent.name
            itemView.itemTrackVoteEventSchedule.text = trackVoteEvent.scheduleBegin.toString()
            itemView.itemTrackVoteEventLocation.text =
                "long: " + trackVoteEvent.longitude + " lat: " + trackVoteEvent.latitude
        }
    }
}