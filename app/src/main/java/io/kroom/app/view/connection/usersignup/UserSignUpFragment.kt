package io.kroom.app.view.connection.usersignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.apollographql.apollo.exception.ApolloException
import com.jk.simple.SimpleSession
import io.kroom.app.view.main.MainActivity
import io.kroom.app.R
import io.kroom.app.view.main.Routes
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.session.Session
import io.kroom.app.view.util.Dialogs
import io.kroom.app.repo.SignWithGoogle_TODO
import org.jetbrains.annotations.Nullable

class UserSignUpFragment : Fragment() {

    /*
    private val users = KroomApolloClient.Users
    private val signWithGoogle = SignWithGoogle_TODO(MainActivity.app)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign up"
        val view = inflater.inflate(R.layout.fragment_user_sign_up, container, false)
        signWithGoogle.setAuthProvider()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpAction?.setOnClickListener {
            this.onSignUp()
        }

        userSignUpGoogle.setOnClickListener {
            signWithGoogle.action()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }

    private fun onSignUp() {

        clearFields()

        MainActivity.app.hideKeyboard()
        signUpAction.isEnabled = false
        signUpLoading.visibility = View.VISIBLE

        Toast
            .makeText(MainActivity.app.applicationContext, "Registering your account...", Toast.LENGTH_SHORT)
            .show()

        users.signUp(getRequest()) { res, exception ->
            res?.let(this::onSuccess)
            exception?.let(this::onFail)

            signUpLoading.visibility = View.INVISIBLE
            signUpAction.isEnabled = true
        }
    }

    private fun getRequest(): KroomApolloClient.Users.UserSignUpRequest {
        val email = signUpEmail.text.toString()
        val password = signUpPassword.text.toString()
        val userName = signUpUsername.text.toString()

        return KroomApolloClient.Users.UserSignUpRequest(userName, email, password)
    }

    override fun onSuccess(s: UserSignUpMutation.UserSignUp) {
        if (checkSignUpFormError(s)) return

        Log.println(Log.INFO, "success-sign-up", "user sign up: $s")

        val user = s.user()
        Dialogs.successDialog(
            MainActivity.app,
            "Your user id = ${user?.id()} email = ${user?.email()} token = ${user?.token()}"
        )
            .show()
        s.user()?.let {
            Session.setUser(it.id()!!, it.email()!!, it.userName(), it.token()!!)
            MainActivity.app.goToRoute(Routes.TRACK_VOTE_EVENT)
        }
    }

    private fun checkSignUpFormError(s: UserSignUpMutation.UserSignUp): Boolean {
        val errors = s.errors()
        if (errors.size > 0) {
            errors.forEach { getFieldByName(it.field())?.error = it.messages()[0] }
            return true
        }
        return false
    }

    override fun onFail(f: ApolloException) {
        Log.println(Log.INFO, "fail-sign-up", "fail: $f")
        Dialogs.errorDialog(MainActivity.app, "You encounter an error ${f.message}")
            .show()

    }

    private fun getFieldByName(name: String): EditText? {
        when (name) {
            "email" -> return signUpEmail
            "pass" -> return signUpPassword
            "userName" -> return signUpUsername
        }
        return null
    }

    private fun clearFields() {
        signUpEmail.error = null
        signUpPassword.error = null
        signUpUsername.error = null
    }
    */
}
