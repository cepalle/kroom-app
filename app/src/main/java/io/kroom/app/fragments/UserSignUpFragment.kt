package io.kroom.app.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.apollographql.apollo.exception.ApolloException
import com.jk.simple.SimpleAuthResult
import com.jk.simple.SimpleSession
import com.jk.simple.idp.IdpType
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.client.KroomClient
import io.kroom.app.graphql.UserSignUpMutation
import io.kroom.app.utils.Dialogs
import io.kroom.app.utils.SuccessOrFail
import kotlinx.android.synthetic.main.fragment_user_sign_up.*
import org.jetbrains.annotations.Nullable

class UserSignUpFragment : Fragment(), SuccessOrFail<UserSignUpMutation.UserSignUp, ApolloException> {

    private val users = KroomClient.UsersRepo
    private lateinit var googletoken: String
    private lateinit var googleEmail :String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign up"
        val view = inflater.inflate(R.layout.fragment_user_sign_up, container, false)
        SimpleSession.setAuthProvider(
            IdpType.GOOGLE,
            "795222071121-0opmpk9tj064mgu8st78jbpirq004m00.apps.googleusercontent.com"
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpAction?.setOnClickListener {
            this.onSignUp()
        }

        googleLogin.setOnClickListener {
            SimpleSession.login(
                this.activity!!, IdpType.GOOGLE
            ) { result -> onLoginCallback(result) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }

    private fun onLoginCallback(result: SimpleAuthResult<Void>) {
        val builder = StringBuilder()
        builder.append(if (result.isSuccess) SimpleSession.getCurrentIdpType().name + " Login is succeed" else "FAIL / " + result.errorCode + " / " + result.errorMessaage)
        if (result.isSuccess) {
            //Toast.makeText(context, "" + builder.toString(), Toast.LENGTH_LONG).show()
            googletoken = SimpleSession.getAccessToken().toString()
            googleEmail = SimpleSession.getEmail().toString()

            this.onGoogleSignUp()
        } else {
            Toast.makeText(context, "" + builder.toString(), Toast.LENGTH_LONG).show()
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
            res?.let {}
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
        // TODO errors

        val user = s.user()
        Dialogs.successDialog(
            Main.app,
            "Your user id = ${user?.id()} email = ${user?.email()} token = ${user?.token()}"
        )
            .show()
    }

    override fun onFail(f: ApolloException) {
        Log.println(Log.INFO, "fail-sign-up", "fail: $f")
        Dialogs.errorDialog(Main.app, "You encounter an error ${f.message}")
            .show()

    }
    private fun onGoogleSignUp(){
        Main.app.hideKeyboard()
        signUpAction.isEnabled = false
        signUpLoading.visibility = View.VISIBLE
        Toast
            .makeText(Main.app.applicationContext, "Registering your account...", Toast.LENGTH_SHORT)
            .show()
        users.signGoogleRequest(getGoogleRequest()){
            res, exception ->
            res?.let{this::onGoogleSuccess}
            exception?.let(this::onFail)

            signUpLoading.visibility = View.INVISIBLE
            signUpAction.isEnabled = true
        }
    }

    private fun getGoogleRequest():KroomClient.UsersRepo.UserGoogleSignRequest{
        val token = googletoken
        return KroomClient.UsersRepo.UserGoogleSignRequest(token)
    }

    override fun onGoogleSuccess(s: UserSignUpMutation.UserSignUp) {
        Log.println(Log.INFO, "success-sign-in", "user sign in: $s")
        // TODO errors

        val user = s.user()
        Dialogs.successDialog(
            Main.app,
            "Your user email = ${googleEmail} token = ${user?.token()}"
        )
            .show()
    }

}
