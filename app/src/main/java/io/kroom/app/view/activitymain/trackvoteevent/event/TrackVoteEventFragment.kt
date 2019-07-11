package io.kroom.app.view.activitymain.trackvoteevent.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activitymain.trackvoteevent.event.add.TrackVoteEventAddFragment
import kotlinx.android.synthetic.main.fragment_event.*

class TrackVoteEventFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Eventlist editor"
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            changeFragment(TrackVoteEventPublicFragment())
        }

        eventListNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.toRoute()?.let(::goToRoute)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun goToRoute(route: Routes) {
        when (route) {
            Routes.PUBLIC -> changeFragment(
                TrackVoteEventPublicFragment()
            )
            Routes.PRIVATE -> changeFragment(
                TrackVoteEventPrivateFragment()
            )
            Routes.ADD -> changeFragment(TrackVoteEventAddFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.commit()
    }

    private enum class Routes(val id: Int) {
        PUBLIC(0),
        PRIVATE(1),
        ADD(2);
    }

    private fun Int.toRoute(): Routes? = Routes.values().find { it.id == this }
}
