package io.kroom.app.view.activitymain.user.activitydeezersignin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R

class DeezerSigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Deezer sign in"
        setContentView(R.layout.activity_deezer_sign_in)
    }

}
