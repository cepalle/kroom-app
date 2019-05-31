package io.kroom.app.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kroom.app.R
import kotlinx.android.synthetic.main.fragment_services_chooser.*
import kotlinx.android.synthetic.main.fragment_start.*

class ServicesChooserFragment : Fragment() {
    private var fragmentTransaction: FragmentTransaction? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Kroom"
        val view = inflater.inflate(R.layout.fragment_services_chooser, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicTrackVote.setOnClickListener{
            fragmentTransaction = fragmentManager!!.beginTransaction().replace(R.id.fragment_container, MusicTrackVoteFragment())
            fragmentTransaction!!.addToBackStack(null)
            fragmentTransaction!!.commit()
        }
        musicControlDelegation.setOnClickListener{
            fragmentTransaction = fragmentManager!!.beginTransaction().replace(R.id.fragment_container, MusicControlDelegationFragment())
            fragmentTransaction!!.addToBackStack(null)
            fragmentTransaction!!.commit()
        }
        musicPlaylistEditor.setOnClickListener{
            fragmentTransaction = fragmentManager!!.beginTransaction().replace(R.id.fragment_container, MusicPlaylistEditorFragement())
            fragmentTransaction!!.addToBackStack(null)
            fragmentTransaction!!.commit()
        }


    }

}