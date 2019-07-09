package io.kroom.app.view.activitymain.playlist.activityplaylisteditor.order

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import io.kroom.app.R


data class playAdapterOrderModel(
    val id: Int,
    val name: String,
    val userName: String,
    val nbTrack: Int,
    val public: Boolean
)

const val EXTRA_NAME_PLAYLIST_ID = "PlaylistPublicAdapter.playlistId"

class PlaylistAdapterOrder(private val dataSet: MutableList<playAdapterOrderModel>, mContext: Context) :
    ArrayAdapter<playAdapterOrderModel>(mContext, R.layout.adapter_playlist_public, dataSet) {

    fun updateDataSet(todos: List<playAdapterOrderModel>) {
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

        // convertView?.setBackgroundColor(dataModel.color.toColor())
        return convertView!!
    }
}
