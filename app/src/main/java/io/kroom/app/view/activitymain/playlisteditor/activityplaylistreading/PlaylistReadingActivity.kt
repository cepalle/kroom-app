package io.kroom.app.view.activitymain.playlisteditor.activityplaylistreading

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R
import io.kroom.app.view.activitymain.playlisteditor.tabs.EXTRA_NAME_PLAYLIST_ID

class PlaylistReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_reading)

        val playlistId: Int = intent.getIntExtra(EXTRA_NAME_PLAYLIST_ID, 0)

    }
}