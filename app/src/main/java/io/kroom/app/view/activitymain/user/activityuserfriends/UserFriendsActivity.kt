package io.kroom.app.view.activitymain.user.activityuserfriends

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R
import io.kroom.app.view.activitymain.user.activityuserfriends.activityfriendsinfo.UserFriendsInfoActivity
import io.kroom.app.view.activitymain.user.activityuserfriends.activityfriendsmanagement.UserFriendsManagementActivity
import kotlinx.android.synthetic.main.activity_user_friends.*

class UserFriendsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Friends"
        setContentView(R.layout.activity_user_friends)

        userFriendsUpdate.setOnClickListener {
            val intent = Intent(this, UserFriendsManagementActivity::class.java)
            startActivity(intent)
        }

        userFriendsInfo.setOnClickListener {
            val intent = Intent(this, UserFriendsInfoActivity::class.java)
            startActivity(intent)
        }

    }
}