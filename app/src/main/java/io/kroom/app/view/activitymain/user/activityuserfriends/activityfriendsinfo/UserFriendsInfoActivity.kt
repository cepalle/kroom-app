package io.kroom.app.view.activitymain.user.activityuserfriends.activityfriendsinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.kroom.app.R
import io.kroom.app.view.activitymain.user.activityuserfriends.EXTRA_NAME_USER_ID
import kotlinx.android.synthetic.main.activity_user_friends_info.*

class UserFriendsInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "User Info"
        setContentView(R.layout.activity_user_friends_info)

        val userId: Int = intent.getIntExtra(EXTRA_NAME_USER_ID, 0)

        userFriendsInfo.text = userId.toString()

    }
}