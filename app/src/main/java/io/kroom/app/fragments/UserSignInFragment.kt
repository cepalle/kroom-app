package io.kroom.app.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.apollographql.apollo.exception.ApolloException
import com.jk.simple.SimpleAuthResult
import com.jk.simple.SimpleSession
import com.jk.simple.idp.IdpType
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.views.util.SuccessOrFail
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_user_sign_in.*
import org.jetbrains.annotations.Nullable

class UserSignInFragment : Fragment(), SuccessOrFail<String, ApolloException> {

    private lateinit var googletoken : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Sign in"

        val view = inflater.inflate(R.layout.fragment_user_sign_in, container, false)
        SimpleSession.setAuthProvider(IdpType.GOOGLE,  "795222071121-p1fbtb3mv3cjo9priggduc335rd8ng4d.apps.googleusercontent.com")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInAction?.setOnClickListener {
            onSignIn()
        }
        googleLogin.setOnClickListener{
            SimpleSession.login(
                this.activity!!, IdpType.GOOGLE
            ) { result -> onLoginCallback(result) }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }

    private fun onLoginCallback(result: SimpleAuthResult<Void>){
        val builder = StringBuilder()
        builder.append(if (result.isSuccess) SimpleSession.getCurrentIdpType().name + " Login is succeed" else "FAIL / " + result.errorCode + " / " + result.errorMessaage)
        if (result.isSuccess)
        {
            googletoken = SimpleSession.getAccessToken()
        }
        else
        {
            Toast.makeText(context, "" + builder.toString(), Toast.LENGTH_LONG).show()
        }

    }

    private fun onSignIn() {
        TODO("not implemented") //send googletoken to authenticate on server
    }

    override fun onSuccess(s: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFail(f: ApolloException) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
