package io.kroom.app.view.activitymain.trackvoteevent.event


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.model.TrackVoteEvent

class RecyclerViewAdapterTrackEvent(context: Context, private val dataSet: MutableList<TrackVoteEvent>) : RecyclerView.Adapter<RecyclerViewAdapterTrackEvent.TrackEventVoteHolder>() {

    private var trackVoteEventList: MutableList<TrackVoteEvent> = dataSet
    private var _context: Context = context


    // private lateinit var trackVoteEvent: List<TrackVoteEvent>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackEventVoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.row_item_track_vote_public_event, parent, false)
        return TrackEventVoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return trackVoteEventList.size
    }

    override fun onBindViewHolder(holder: TrackEventVoteHolder, position: Int) {
        val event: TrackVoteEvent = trackVoteEventList[position]
        holder.userMasterEvent.text = event.userMaster.userName
        holder.nameEvent.text = event.name
        holder.locationEvent.text = "long: " + event.longitude + " lat: " + event.latitude
    }

    inner class TrackEventVoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

         val userMasterEvent: TextView
         val nameEvent: TextView
         val scheduleEvent: TextView
         val locationEvent: TextView
        init {
            userMasterEvent = itemView.findViewById(R.id.itemTrackVoteEventUserMaster)
            nameEvent = itemView.findViewById(R.id.itemTrackVoteEventName)
            scheduleEvent = itemView.findViewById(R.id.itemTrackVoteEventSchedule)
            locationEvent = itemView.findViewById(R.id.itemTrackVoteEventLocation)
        }

        override fun onClick(view: View) {

            when (itemView.getId()) {

                R.id.linearlayoutEventPublic -> Toast.makeText(_context, "show event", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
