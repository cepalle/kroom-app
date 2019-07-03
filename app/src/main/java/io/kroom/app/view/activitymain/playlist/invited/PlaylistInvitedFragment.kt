package io.kroom.app.view.activitymain.playlist.invited

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import io.kroom.app.graphql.PlayListEditorByUserIdQuery
import io.kroom.app.view.activitymain.playlist.PlaylistPublicAdapter
import io.kroom.app.view.activitymain.playlist.playAdapterModel
import kotlinx.android.synthetic.main.fragment_playlist_tab_invited.*

class PlaylistInvitedFragment : Fragment() {

    private val adapterIvited by lazy {
        context?.let {
            PlaylistPublicAdapter(arrayListOf(), it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_tab_invited, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (playlistInvitedList.adapter == null) {
            playlistInvitedList.adapter = adapterIvited
        }

        val model = ViewModelProviders.of(this).get(PlaylistInvitedViewModel::class.java)

        val listInvited = model.getListInvited()

        updatePlaylistInvited(listInvited?.value)
        listInvited?.observe(this, Observer {
            updatePlaylistInvited(it)
        })

    }

    private fun updatePlaylistInvited(res: Result<PlayListEditorByUserIdQuery.Data>?) {
        if (res == null) return
        res.onFailure {
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
        res.onSuccess {
            it.PlayListEditorByUserId().playListEditor()?.mapNotNull {
                val userName = it.userMaster()?.userName()
                val nbTrack = it.tracks()?.count()

                if (userName != null && nbTrack != null)
                    playAdapterModel(
                        it.id(),
                        it.name(),
                        userName,
                        nbTrack,
                        it.public_()
                    )
                else null
            }?.let {
                adapterIvited?.updateDataSet(it)
                adapterIvited?.notifyDataSetChanged()
            }
        }
    }

}