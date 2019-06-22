package io.kroom.app.view.main.playlisteditor.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apollographql.apollo.api.Response
import io.kroom.app.R
import io.kroom.app.graphql.PlayListEditorsPublicQuery

class PlaylistPublicFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val model = ViewModelProviders.of(this).get(PlaylistPublicViewModel::class.java)

        updatePlaylistPublic(model.playlistPublic.value)
        model.playlistPublic.observe(this, Observer {
            updatePlaylistPublic(it)
        })

        return inflater.inflate(R.layout.fragment_playlist_editor_tab_public, container, false)
    }

    private fun updatePlaylistPublic(res: Result<Response<PlayListEditorsPublicQuery.Data>>?) {
        if (res == null) return


    }

}