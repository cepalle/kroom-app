package io.kroom.app.view.main.playlisteditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.main.playlisteditor.tabs.PlaylistAddFragment
import io.kroom.app.view.main.playlisteditor.tabs.PlaylistPrivateFragment
import io.kroom.app.view.main.playlisteditor.tabs.PlaylistPublicFragment
import kotlinx.android.synthetic.main.fragment_playlist_editor.*

class PlaylistEditorFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Playlist editor"
        changeFragment(PlaylistPublicFragment())
        return inflater.inflate(R.layout.fragment_playlist_editor, container, false)
    }

    override fun onStart() {
        super.onStart()

        tabs_playlist_navigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
            Routes.PRIVATE -> changeFragment(PlaylistPrivateFragment())
            Routes.ADD -> changeFragment(PlaylistAddFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.tab_navigation_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private enum class Routes(val id: Int) {
        PUBLIC(0),
        PRIVATE(1),
        ADD(2);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}