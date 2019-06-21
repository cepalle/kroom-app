package io.kroom.app.view.TMP.fragment_TOSORT

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.view.main.MainActivity
import io.kroom.app.R
import io.kroom.app.view.main.Routes

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Settings"
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // settingsFriends.setOnClickListener { MainActivity.app.goToRoute(Routes.USER_FRIENDS) }
        // settingsDebug.setOnClickListener { MainActivity.app.goToRoute(Routes.DEBUG)  }
        // settingsProfile.setOnClickListener {  }
    }
}
