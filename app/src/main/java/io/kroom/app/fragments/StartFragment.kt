package io.kroom.app.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.kroom.app.R
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {
    private var fragmentTransaction: FragmentTransaction? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Kroom"
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startConnexion.setOnClickListener {
            fragmentTransaction =
                fragmentManager!!.beginTransaction().replace(R.id.fragment_container, UserSignInFragment())
            fragmentTransaction!!.addToBackStack(null)
            fragmentTransaction!!.commit()
        }
        startInscription.setOnClickListener {
            fragmentTransaction =
                fragmentManager!!.beginTransaction().replace(R.id.fragment_container, UserSignUpFragment())
            fragmentTransaction!!.addToBackStack(null)
            fragmentTransaction!!.commit()
        }


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDetach() {
        super.onDetach()


    }
}