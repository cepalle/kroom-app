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
import io.kroom.app.graphql.PlayListEditorsPublicQuery
import kotlinx.android.synthetic.main.fragment_playlist_tab_public.*

class PlaylistPublicFragment : Fragment() {

    private val adapterPublic by lazy {
        context?.let {
            PlaylistPublicAdapter(arrayListOf(), it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist_tab_public, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (playlistPublicList.adapter == null) {
            playlistPublicList.adapter = adapterPublic
            adapterPublic?.updateDataSet(
                listOf(
                    playAdapterModel(0, "name", "username", 0, false)
                )
            )
        }

        val model = ViewModelProviders.of(this).get(PlaylistPublicViewModel::class.java)

        val listPublic = model.getListPublic()

        updatePlaylistPublic(listPublic.value)
        listPublic.observe(this, Observer {
            updatePlaylistPublic(it)
        })

    }

    private fun updatePlaylistPublic(res: Result<PlayListEditorsPublicQuery.Data>?) {
        if (res == null) return
        res.onFailure {
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
        res.onSuccess {
            it.PlayListEditorsPublic().map {
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
            }.filterNotNull().let {
                adapterPublic?.updateDataSet(it)
            }
        }
    }

}