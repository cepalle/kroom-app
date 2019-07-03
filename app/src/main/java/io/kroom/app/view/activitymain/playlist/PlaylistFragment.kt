package io.kroom.app.view.activitymain.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.playlist.add.PlaylistAddFragment
import io.kroom.app.view.activitymain.playlist.invited.PlaylistInvitedFragment
import io.kroom.app.view.activitymain.playlist.publc.PlaylistPublicFragment
import kotlinx.android.synthetic.main.fragment_playlist.*

class PlaylistFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Playlist editor"
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            changeFragment(PlaylistPublicFragment())
        }

        playlistNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.toRoute()?.let(::goToRoute)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.PUBLIC -> changeFragment(PlaylistPublicFragment())
            Routes.PRIVATE -> changeFragment(PlaylistInvitedFragment())
            Routes.ADD -> changeFragment(PlaylistAddFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.playlistNavigationContainer, fragment)
            ?.commit()
    }

    private enum class Routes(val id: Int) {
        PUBLIC(0),
        PRIVATE(1),
        ADD(2);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}