package io.kroom.app.view.activitymain.user.activityuserfriends.activityfriendsinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R

class UserFriendsInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "User Info"
        setContentView(R.layout.activity_user_friends_info)
    }
}