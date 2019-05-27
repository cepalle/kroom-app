package io.kroom.app.views


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.Routes
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Kroom"

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeToSignIn.setOnClickListener {
            Main.app.goToRoute(Routes.USER_SIGN_IN)
        }

        homeToSignUp.setOnClickListener {
            Main.app.goToRoute(Routes.USER_SIGN_UP)
        }
    }
}