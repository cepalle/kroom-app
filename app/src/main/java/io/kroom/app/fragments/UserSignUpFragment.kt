package io.kroom.app.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.client.KroomClient
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.views.util.Dialogs
import io.kroom.app.views.util.SuccessOrFail
import kotlinx.android.synthetic.main.fragment_user_sign_up.*

class UserSignUpFragment : Fragment(), SuccessOrFail<UserSignUpMutation.UserSignUp, ApolloException> {

    private val users = KroomClient.UsersRepo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign up"
        return inflater.inflate(R.layout.fragment_user_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpAction?.setOnClickListener {
            this.onSignUp()
        }
    }

    private fun onSignUp() {
        Main.app.hideKeyboard()
        signUpAction.isEnabled = false
        signUpLoading.visibility = View.VISIBLE



        Toast
            .makeText(Main.app.applicationContext, "Registering your account...", Toast.LENGTH_SHORT)
            .show()

        users.signUp(getRequest()) { res, exception ->
            res?.let(this::onSuccess)
            exception?.let(this::onFail)

            signUpLoading.visibility = View.INVISIBLE
            signUpAction.isEnabled = true
        }
    }

    private fun getRequest(): KroomClient.UsersRepo.UserSignUpRequest {
        val email = signUpEmail.text.toString()
        val password = signUpPassword.text.toString()
        val userName = signUpUsername.text.toString()

        return KroomClient.UsersRepo.UserSignUpRequest(email, password, userName)
    }

    override fun onSuccess(s: UserSignUpMutation.UserSignUp) {
        Log.println(Log.INFO, "success-sign-up", "user sign up: $s")
        Dialogs.successDialog(Main.app, "Your user id = ${s.id()} email = ${s.email()} token = ${s.token()}")
            .show()
    }

    override fun onFail(f: ApolloException) {
        Log.println(Log.INFO, "fail-sign-up", "fail: $f")
        Dialogs.errorDialog(Main.app, "You encounter an error ${f.message}")
            .show()

    }
}
