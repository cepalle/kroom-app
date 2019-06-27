package io.kroom.app.view.activitymain.playlisteditor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R

class PlaylistEditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_editor)

        val message: Int = intent.getIntExtra("", 0)
        Log.i("TEST____", message.toString())

    }

}
