package io.kroom.app.view.activitymain.playlist.activityplaylisteditor.order

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import kotlinx.android.synthetic.main.fragment_playlist_editor_tab_order.*


class PlaylistEditorOrderFragement(val playlistId: Int) : Fragment() {

    private lateinit var adapterAutocompletion: ArrayAdapter<String>
    private lateinit var adapterTracks: PlaylistAdapterOrder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_editor_tab_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fun updateAutoCompletion(ls: List<AutoCompletView>?) {
            ls ?: return
            adapterAutocompletion.clear()
            adapterAutocompletion.addAll(ls.map { it.str })
            adapterAutocompletion.notifyDataSetChanged()
        }

        fun updateListTracks(ls: List<TrackAdapterOrderModel>?) {
            ls ?: return
            adapterTracks.updateDataSet(ls)
            adapterTracks.notifyDataSetChanged()
        }

        super.onViewCreated(view, savedInstanceState)

        adapterAutocompletion = ArrayAdapter(activity!!, R.layout.select_dialog_item_material, java.util.ArrayList())
        playlistEditorTabOrderAutoCompleteTextView.setAdapter(adapterAutocompletion)

        val model = ViewModelProviders.of(
            this,
            PlaylistEditorOrderViewModelFactory(this.activity!!.application, playlistId)
        ).get(PlaylistEditorOrderViewModel::class.java)
        val autoCompletion = model.getAutoCompletion()
        val tracksList = model.getTracksList()
        val errorMessage = model.getErrorMessage()


        adapterTracks = PlaylistAdapterOrder(arrayListOf(), context!!, model)

        playlistEditorTabOrderAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                model.updateAutoComplet(playlistEditorTabOrderAutoCompleteTextView.text.toString())
            }
        })

        updateAutoCompletion(autoCompletion.value)
        autoCompletion.observe(this, Observer {
            updateAutoCompletion(it)
        })

        errorMessage.observe(this, Observer {
            playlistEditorTabOrderAutoCompleteTextView.error = it
        })


        updateListTracks(tracksList.value)
        tracksList.observe(this, Observer {
            updateListTracks(it)
        })
    }

}