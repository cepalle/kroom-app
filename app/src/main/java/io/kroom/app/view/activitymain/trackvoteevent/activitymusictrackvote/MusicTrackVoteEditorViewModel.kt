package io.kroom.app.view.activitymain.trackvoteevent.activitymusictrackvote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.kroom.app.util.Session
import io.kroom.app.webservice.GraphClient

class MusicTrackVoteEditorViewModel(app: Application, private val eventId: Int) : AndroidViewModel(app) {
    private val client = GraphClient {
        Session.getToken(getApplication())
    }.client

}
