package io.kroom.app.view.connection.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.view.main.MainActivity
import io.kroom.app.R
import io.kroom.app.view.main.Routes

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Kroom"
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // homeSignIn.setOnClickListener {
        //     MainActivity.app.goToRoute(Routes.USER_SIGN_IN)
        // }
        // homeSignUp.setOnClickListener {
        //     MainActivity.app.goToRoute(Routes.USER_SIGN_UP)
        // }

    }
}