package io.kroom.app.view.activitymain.playlisteditor.activityplaylisteditor

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class PlaylistEditorViewModel(app: Application) : AndroidViewModel(app) {
    var tabPosition: Int = 0
}