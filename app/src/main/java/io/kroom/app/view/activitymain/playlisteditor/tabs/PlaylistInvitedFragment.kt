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
import io.kroom.app.graphql.PlayListEditorByUserIdQuery

class PlaylistInvitedFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_editor_tab_invited, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(this).get(PlaylistInvitedViewModel::class.java)

        val listInvited = model.getListInvited()

        updatePlaylistInvited(listInvited.value)
        listInvited.observe(this, Observer {
            updatePlaylistInvited(it)
        })

    }

    private fun updatePlaylistInvited(res: Result<Response<PlayListEditorByUserIdQuery.Data>>?) {
        if (res == null) return
        // TODO update view
    }

}