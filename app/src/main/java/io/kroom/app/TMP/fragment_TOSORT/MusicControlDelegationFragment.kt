package io.kroom.app.TMP.fragment_TOSORT

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kroom.app.R

class MusicControlDelegationFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Control delegation"
        return inflater.inflate(R.layout.fragment_music_control_delegation, container, false)
    }
}