package io.kroom.app.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.views.util.SuccessOrFail
import kotlinx.android.synthetic.main.fragment_user_sign_in.*

class UserSignInFragment : Fragment(), SuccessOrFail<String, ApolloException> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign in"

        return inflater.inflate(R.layout.fragment_user_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInAction?.setOnClickListener {
            onSignIn()
        }
    }


    private fun onSignIn() {

    }

    override fun onSuccess(s: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFail(f: ApolloException) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
