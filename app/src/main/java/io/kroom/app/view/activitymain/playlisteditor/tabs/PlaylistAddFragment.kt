package io.kroom.app.view.activitymain.playlisteditor.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import kotlinx.android.synthetic.main.fragment_playlist_editor_tab_add.*

class PlaylistAddFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_editor_tab_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(this).get(PlaylistAddViewModel::class.java)

        btn_new_playlist.setOnClickListener {
            val inputName = input_name_edit.text.toString()
            val public = switch_public.isChecked
            model.newPlaylist(inputName, public)
        }
    }

}