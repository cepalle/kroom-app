package io.kroom.app.view.activitymain.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R
import io.kroom.app.view.activitymain.user.activityuserfriends.UserFriendsActivity
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragement : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "User"
        return inflater.inflate(R.layout.activity_user_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDeezerSignIn.setOnClickListener {
        }

        userPreferenceMusical.setOnClickListener {

        }

        userFriendsButton.setOnClickListener {
            val intent = Intent(context, UserFriendsActivity::class.java)
            startActivity(intent)
        }

    }

}
