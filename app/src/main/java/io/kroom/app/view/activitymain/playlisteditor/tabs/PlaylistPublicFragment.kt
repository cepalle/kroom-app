package io.kroom.app.view.activitymain.playlisteditor.tabs

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

    private val adapterPublic by lazy {
        context?.let {
            PlaylistPublicAdapter(arrayListOf(), it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_editor_tab_public, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(this).get(PlaylistPublicViewModel::class.java)

        val listPublic = model.getListPublic()

        updatePlaylistPublic(listPublic.value)
        listPublic.observe(this, Observer {
            updatePlaylistPublic(it)
        })

    }

    private fun updatePlaylistPublic(res: Result<Response<PlayListEditorsPublicQuery.Data>>?) {
        if (res == null) return
        // TODO update view
        adapterPublic?.updateDataSet()
    }

}