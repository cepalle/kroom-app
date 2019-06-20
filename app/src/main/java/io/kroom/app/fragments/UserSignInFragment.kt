package io.kroom.app.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.apollographql.apollo.exception.ApolloException
import com.jk.simple.SimpleAuthResult
import com.jk.simple.SimpleSession
import com.jk.simple.idp.IdpType
import io.kroom.app.Main

import io.kroom.app.R
import io.kroom.app.Routes
import io.kroom.app.client.KroomClient
import io.kroom.app.graphql.UserSignInMutation
import io.kroom.app.graphql.UserSignWhithGoolgeMutation
import io.kroom.app.session.Session
import io.kroom.app.utils.SuccessOrFail
import io.kroom.app.utils.Dialogs
import io.kroom.app.utils.SignWithGoogle

import kotlinx.android.synthetic.main.fragment_user_sign_in.*


import org.jetbrains.annotations.Nullable

class UserSignInFragment : Fragment(), SuccessOrFail<UserSignInMutation.UserSignIn, ApolloException> {

    private val users = KroomClient.Users

    private val signWithGoogle = SignWithGoogle(Main.app)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign in"

        val view = inflater.inflate(R.layout.fragment_user_sign_in, container, false)
        signWithGoogle.setAuthProvider()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInAction.setOnClickListener {
            onSignIn()
        }

        signInForgotPassword.setOnClickListener { Main.app.goToRoute(Routes.USER_FORGOT_PASSWORD) }

        userSignInGoogle.setOnClickListener {
            signWithGoogle.action()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }

    private fun onSignIn() {
        clearFields()

        Main.app.hideKeyboard()
        signInAction.isEnabled = false
        signInLoading.visibility = View.VISIBLE

        Toast
            .makeText(Main.app.applicationContext, "Login your account...", Toast.LENGTH_SHORT)
            .show()

        users.signIn(getRequest()) { res, exception ->
            res?.let(this::onSuccess)
            exception?.let(this::onFail)

            signInLoading.visibility = View.INVISIBLE
            signInAction.isEnabled = true
        }
    }


    override fun onSuccess(s: UserSignInMutation.UserSignIn) {
        if (checkSignInFormError(s)) return

        Log.println(Log.INFO, "success-sign-in", "user sign up: $s")

        s.user()?.let {
            Session.setUser(it.id()!!, it.email()!!, it.userName(), it.token()!!)
            Main.app.goToRoute(Routes.HOME)
        }
    }


    private fun checkSignInFormError(s: UserSignInMutation.UserSignIn): Boolean {
        val errors = s.errors()
        if (errors.size > 0) {
            errors.forEach { getFieldByName(it.field())?.error = it.messages()[0] }
            return true
        }
        return false
    }

    override fun onFail(f: ApolloException) {
        Log.println(Log.INFO, "fail-sign-in", "fail: $f")
        Dialogs.errorDialog(Main.app, "You encounter an error ${f.message}")
            .show()
    }

    private fun getFieldByName(name: String): EditText? {
        when (name) {
            "pass" -> return signInUsername
            "userName" -> return signInPassword
        }
        return null
    }

    private fun getRequest(): KroomClient.Users.UserSignInRequest {
        val password = signInPassword.text.toString()
        val userName = signInUsername.text.toString()

        return KroomClient.Users.UserSignInRequest(userName, password)
    }

    private fun clearFields() {
        signInPassword.error = null
        signInUsername.error = null
    }
}
