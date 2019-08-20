package io.kroom.app.view.activitymain.user.activityusermusicpreference

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R

class MusicPreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Music preference"
        setContentView(R.layout.activity_music_preference)
    }

}