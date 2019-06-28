package io.kroom.app.view.activityauth.usersignup

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
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.util.SharedPreferences
import kotlinx.android.synthetic.main.fragment_user_sign_up.*

class UserSignUpFragment : Fragment() {


    lateinit var model: UserSignUpViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign up"
        return inflater.inflate(R.layout.fragment_user_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this).get(UserSignUpViewModel::class.java)

        signUpAction.setOnClickListener {
            this.onSignUp()
        }

        model.getSignUpResult().observe(this, Observer {
            observe(it)
        })

        userSignUpGoogle.setOnClickListener {
            Log.i("todo", "a faire")
        }
    }

    private fun onSignUp() {

        clearFields()


        signUpAction.isEnabled = false
        signUpLoading.visibility = View.VISIBLE

        Toast
            .makeText(context, "Registering your account...", Toast.LENGTH_SHORT)
            .show()

        model.signUp(signUpUsername.text.toString(), signUpEmail.text.toString(), signUpPassword.text.toString())
    }

    private fun observe(result: Result<UserSignUpMutation.Data>) {
        result.onFailure(this::onFail)
        result.onSuccess {
            val userSignUp = it.UserSignUp()
            if (userSignUp.errors().isNotEmpty()) {
                userSignUp.errors().forEach { err -> getFieldByName(err.field())?.error = err.messages()[0] }
                return@onSuccess
            }

            userSignUp.user()?.let(this::onSuccess)
        }

        signUpLoading.visibility = View.INVISIBLE
        signUpAction.isEnabled = true
    }


    fun onSuccess(user: UserSignUpMutation.User) {

        Log.i("success-sign-up", "user sign up: $user")


        SharedPreferences.setUser(
            this.activity?.application!!,
            user.id()!!, user.email()!!, user.userName(), user.token()!!
        )

        activity?.finish()
    }

    private fun onFail(f: Throwable) {
        Log.println(Log.INFO, "fail-sign-up", "fail: $f")
        Dialogs.errorDialog(context!!, "You encounter an error ${f.message}")
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

}
