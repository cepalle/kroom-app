package io.kroom.app.view.activitymain.playlist.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import kotlinx.android.synthetic.main.fragment_playlist_tab_add.*

class PlaylistAddFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_tab_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(this).get(PlaylistAddViewModel::class.java)

        model.result().observe(this, Observer {
            it.onSuccess {
                if (it.PlayListEditorNew().errors().isEmpty()) {
                    Toast.makeText(context, "Playlist created", Toast.LENGTH_SHORT).show()
                    playlistAddNameEdit.setText("")
                } else {
                    playlistAddNameEdit.error = it.PlayListEditorNew().errors()[0].messages()[0]
                }
            }
            it.onFailure {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })

        playlistAddBtnNew.setOnClickListener {
            val inputName = playlistAddNameEdit.text.toString()
            val public = playlistAddSwitchPublic.isChecked
            model.newPlaylist(inputName, public)
        }
    }

}