package io.kroom.app.view.activitymain.trackvoteevent


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import io.kroom.app.R
import io.kroom.app.graphql.TrackVoteEventsPublicQuery
import io.kroom.app.view.activitymain.model.TrackVoteEvent

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
        val event : TrackVoteEvent = trackVoteEventList[position]

    }

    inner class TrackEventVoteHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        private val itemEventTrackPublic : LinearLayout

        init{

            itemEventTrackPublic = itemView.findViewById(R.id.linearlayoutEventPublic)

        }
        override fun onClick(view: View){

       /*     listener.onContactSelected(_contact.get(adapterPosition))
            notifyItemChanged(lastIndexContactSelected)
            lastIndexContactSelected = adapterPosition
            indexContactSelected = -1
            notifyItemChanged(adapterPosition)*/

        }


    }

    /*inner class ContactsHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val item_hide: LinearLayout
        private val item_visible: LinearLayout
        private val imageView: ImageView
        private val textFulltName: TextView
        private val callButton: ImageButton
        private val smsButton: ImageButton

        init {

            item_hide = itemView.findViewById(R.id.linearlayout2)
            item_visible = itemView.findViewById(R.id.linearlayout1)
            imageView = itemView.findViewById(R.id.avatar)
            textFulltName = itemView.findViewById(R.id.textViewFullname)
            callButton = itemView.findViewById(R.id.phone)
            smsButton = itemView.findViewById(R.id.sms)

            item_visible.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            indexContactSelected = adapterPosition
            if (lastIndexContactSelected == indexContactSelected) {

                //set visible item_hide
                if (_contact.get(lastIndexContactSelected).isExpanded() === false) {
                    _contact.get(lastIndexContactSelected).setExpanded(false)
                    _contact.get(indexContactSelected).setExpanded(false)
                    notifyItemChanged(adapterPosition)
                }
            } else if (lastIndexContactSelected != -1) {
                _contact.get(lastIndexContactSelected).setExpanded(false)
            }
            listener.onContactSelected(_contact.get(adapterPosition))
            notifyItemChanged(lastIndexContactSelected)
            lastIndexContactSelected = adapterPosition
            indexContactSelected = -1
            notifyItemChanged(adapterPosition)
        }
    }*/
}

/*
data class playAdapterModel(
    val id: Int,
    val name: String,
    val userName: String,
    val nbTrack: Int,
    val public: Boolean
)

const val EXTRA_NAME_PLAYLIST_ID = "PlaylistPublicAdapter.playlistId"

class PlaylistPublicAdapter(private val dataSet: MutableList<playAdapterModel>, mContext: Context) :
    ArrayAdapter<playAdapterModel>(mContext, R.layout.adapter_playlist_public, dataSet) {

    fun updateDataSet(todos: List<playAdapterModel>) {
        dataSet.clear()
        dataSet.addAll(todos)
    }

    private class ViewHolder {
        var cacheName: TextView? = null
        var cacheUserName: TextView? = null
        var cacheNbTrack: TextView? = null
        var cachePrivacy: TextView? = null
        var cacheButtonReading: Button? = null
        var cacheButtonEdition: Button? = null
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val dataModel = dataSet[position]
        val viewHolder: ViewHolder // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.adapter_playlist_public, parent, false)
            viewHolder.cacheName = convertView.findViewById(R.id.adapter_name)
            viewHolder.cacheNbTrack = convertView.findViewById(R.id.adapter_nb_track)
            viewHolder.cachePrivacy = convertView.findViewById(R.id.adapter_privacy)
            viewHolder.cacheUserName = convertView.findViewById(R.id.adapter_user_name)
            viewHolder.cacheButtonReading = convertView.findViewById(R.id.adapter_button_reading)
            viewHolder.cacheButtonEdition = convertView.findViewById(R.id.adapter_button_edition)

            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.cacheName?.text = dataModel.name
        viewHolder.cacheUserName?.text = "by ${dataModel.userName}"
        viewHolder.cacheNbTrack?.text = "${dataModel.nbTrack} Tracks"
        viewHolder.cachePrivacy?.text = if (dataModel.public) "public" else "private"
        viewHolder.cacheButtonEdition?.setOnClickListener {
            val intent = Intent(context, PlaylistEditorActivity::class.java).apply {
                putExtra(EXTRA_NAME_PLAYLIST_ID, dataModel.id)
            }
            startActivity(context, intent, null)
        }
        viewHolder.cacheButtonReading?.setOnClickListener {
            val intent = Intent(context, PlaylistReadingActivity::class.java).apply {
                putExtra(EXTRA_NAME_PLAYLIST_ID, dataModel.id)
            }
            startActivity(context, intent, null)
        }

        // convertView?.setBackgroundColor(dataModel.color.toColor())
        return convertView!!
    }
}
 */