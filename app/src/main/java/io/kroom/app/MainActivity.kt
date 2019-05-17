package io.kroom.app

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import io.kroom.app.client.KroomClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        buttonSuperYolo.setOnClickListener {
            KroomClient.trackById(3135556)
        }
    }


}
