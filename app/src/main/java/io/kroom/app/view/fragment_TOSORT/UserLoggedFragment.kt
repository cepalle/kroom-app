package io.kroom.app.view.fragment_TOSORT

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jk.simple.SimpleSession
import io.kroom.app.view.main.MainActivity
import io.kroom.app.R
import io.kroom.app.view.main.Routes
import io.kroom.app.session.Session

class UserLoggedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Kroom"
        return inflater.inflate(R.layout.fragment_user_logged, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!Session.isConnected()) {
            MainActivity.app.goToRoute(Routes.TRACK_VOTE_EVENT)
        }

        loggedLogoutAction.setOnClickListener {
            Session.removeUser()
            if (SimpleSession.isSignedIn(MainActivity.app)) {
                SimpleSession . logout ()

            }
            MainActivity.app.goToRoute(Routes.TRACK_VOTE_EVENT)
        }


        loggedWelcomeText.text = "Welcome ${Session.getUsername()} !"
        println("TOKEN USER ${Session.getToken()}")
    }
}