package io.kroom.app.view.connection.usersignIn

import androidx.fragment.app.Fragment

class UserSignInFragment : Fragment() {
    /*
    private val users = KroomApolloClient.Users

    private val signWithGoogle = SignWithGoogle_TODO(MainActivity.app)


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

        signInForgotPassword.setOnClickListener { MainActivity.app.goToRoute(Routes.USER_FORGOT_PASSWORD) }

        userSignInGoogle.setOnClickListener {
            signWithGoogle.action()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }

    private fun onSignIn() {
        clearFields()

        MainActivity.app.hideKeyboard()
        signInAction.isEnabled = false
        signInLoading.visibility = View.VISIBLE

        Toast
            .makeText(MainActivity.app.applicationContext, "Login your account...", Toast.LENGTH_SHORT)
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
            ScharedPreferencesRepo.setUser(it.id()!!, it.email()!!, it.userName(), it.token()!!)
            MainActivity.app.goToRoute(Routes.TRACK_VOTE_EVENT)
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
        Dialogs.errorDialog(MainActivity.app, "You encounter an error ${f.message}")
            .show()
    }

    private fun getFieldByName(name: String): EditText? {
        when (name) {
            "pass" -> return signInUsername
            "userName" -> return signInPassword
        }
        return null
    }

    private fun getRequest(): KroomApolloClient.Users.UserSignInRequest {
        val password = signInPassword.text.toString()
        val userName = signInUsername.text.toString()

        return KroomApolloClient.Users.UserSignInRequest(userName, password)
    }

    private fun clearFields() {
        signInPassword.error = null
        signInUsername.error = null
    }
    */
}
