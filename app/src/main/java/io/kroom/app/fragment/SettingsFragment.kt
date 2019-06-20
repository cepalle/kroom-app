package io.kroom.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.Routes
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Settings"
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsFriends.setOnClickListener { Main.app.goToRoute(Routes.USER_FRIENDS) }
        settingsDebug.setOnClickListener { Main.app.goToRoute(Routes.DEBUG)  }
        settingsProfile.setOnClickListener {  }
    }
}
