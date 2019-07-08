package io.kroom.app.view.activitymain.playlist.activityplaylisteditor.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R


class PlaylistEditorOrderFragement(val playlistId: Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_editor_tab_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(
            this,
            PlaylistEditorOrderViewModelFactory(this.activity!!.application, playlistId)
        ).get(PlaylistEditorOrderViewModel::class.java)
        val autoCompletion = model.getAutoCompletion()
        val tracksList = model.getTracksList()
        val errorMessage = model.getErrorMessage()

    }

}
