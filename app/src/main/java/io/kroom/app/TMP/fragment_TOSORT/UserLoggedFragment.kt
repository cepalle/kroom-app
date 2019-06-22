package io.kroom.app.TMP.fragment_TOSORT

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R
import io.kroom.app.SharedPreferencesViewModel

class UserLoggedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Kroom"
        return inflater.inflate(R.layout.fragment_user_logged, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        loggedLogoutAction.setOnClickListener {
            SharedPreferencesViewModel.removeUser()
            if (SimpleSession.isSignedIn(MainActivity.app)) {
                SimpleSession . logout ()

            }
            MainActivity.app.goToRoute(Routes.TRACK_VOTE_EVENT)
        }


        loggedWelcomeText.text = "Welcome ${SharedPreferencesViewModel.getUsername()} !"
        */
        println("TOKEN USER ${SharedPreferencesViewModel.getToken()}")
    }
}