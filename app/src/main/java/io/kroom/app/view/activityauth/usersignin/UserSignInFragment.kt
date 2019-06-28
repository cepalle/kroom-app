package io.kroom.app.view.activityauth.usersignin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import io.kroom.app.TMP.util.Dialogs
import io.kroom.app.graphql.UserSignInMutation
import io.kroom.app.util.Session
import kotlinx.android.synthetic.main.fragment_user_sign_in.*

class UserSignInFragment : Fragment() {

    lateinit var model: UserSignInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign in"
        return inflater.inflate(R.layout.fragment_user_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this).get(UserSignInViewModel::class.java)


        signInAction.setOnClickListener { onSignIn() }

        signInForgotPassword.setOnClickListener { Log.e("TODO", "blsblsblalvlal") }

        model.getSignInResult().observe(this, Observer { observe(it) })
    }


    private fun onSignIn() {
        Log.i("TEST", "in onSignIn")
        clearFields()

        signInAction.isEnabled = false
        signInLoading.visibility = View.VISIBLE

        Toast
            .makeText(context, "Login your account...", Toast.LENGTH_SHORT)
            .show()

        model.signIn(signInUsername.text.toString(), signInPassword.text.toString())
    }


    private fun observe(result: Result<UserSignInMutation.Data>) {
        Log.i("TEST", "in observe")
        result.onFailure(this::onFail)
        result.onSuccess {
            Log.i("TEST", "in observe -> onSuccess")

            val userSignIn = it.UserSignIn()
            if (userSignIn.errors().isNotEmpty()) {
                userSignIn.errors().forEach { err -> getFieldByName(err.field())?.error = err.messages()[0] }
                return@onSuccess
            }

            userSignIn.user()?.let(this::onSuccess)
        }

        signInLoading.visibility = View.INVISIBLE
        signInAction.isEnabled = true
    }

    private fun onSuccess(user: UserSignInMutation.User) {
        Log.i("success-sign-in", "user sign up: $user")

        Session.setUser(
            this.activity?.application!!,
            user.id()!!, user.email()!!, user.userName(), user.token()!!
        )

        activity?.finish()
    }


    private fun onFail(f: Throwable) {
        Log.i("fail-sign-in", "fail: $f")

        Dialogs.errorDialog(context!!, "You encounter an error ${f.message}")
            .show()
    }

    private fun getFieldByName(name: String): EditText? {
        when (name) {
            "pass" -> return signInUsername
            "userName" -> return signInPassword
        }
        return null
    }

    private fun clearFields() {
        signInPassword.error = null
        signInUsername.error = null
    }

}
