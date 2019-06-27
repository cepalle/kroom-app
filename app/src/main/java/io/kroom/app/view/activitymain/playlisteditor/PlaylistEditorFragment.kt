package io.kroom.app.view.activitymain.playlisteditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.playlisteditor.tabs.PlaylistAddFragment
import io.kroom.app.view.activitymain.playlisteditor.tabs.PlaylistInvitedFragment
import io.kroom.app.view.activitymain.playlisteditor.tabs.PlaylistPublicFragment
import kotlinx.android.synthetic.main.fragment_playlist_editor.*

class PlaylistEditorFragment : Fragment() {
    var model: PlaylistEditorViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Playlist editor"
        return inflater.inflate(R.layout.fragment_playlist_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            changeFragment(PlaylistPublicFragment())
        }

        model = ViewModelProviders.of(this).get(PlaylistEditorViewModel::class.java)

        model?.let {
            tabs_playlist_navigation.setScrollPosition(it.tabPosition, 0.0f, true)
        }

        tabs_playlist_navigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.toRoute()?.let(::goToRoute)
                model?.tabPosition = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                if (tab.position != model?.tabPosition) {
                    tab.position.toRoute()?.let(::goToRoute)
                    model?.tabPosition = tab.position
                }
            }
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