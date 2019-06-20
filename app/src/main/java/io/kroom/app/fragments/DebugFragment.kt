package io.kroom.app.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jk.simple.SimpleAuthResult
import com.jk.simple.SimpleSession
import com.jk.simple.SimpleSession.login
import com.jk.simple.idp.IdpType
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.Routes
import kotlinx.android.synthetic.main.fragment_debug.*
import org.jetbrains.annotations.Nullable


class DebugFragment : Fragment() {

    private var tokenTextView: TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Kroom"


        val view = inflater.inflate(R.layout.fragment_debug, container, false)
        tokenTextView = view.findViewById(R.id.tokenView)
        SimpleSession.setAuthProvider(IdpType.GOOGLE,  "795222071121-82h8a0k3bhafm1jekm9k8eipmpvcshgr.apps.googleusercontent.com")
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }

    private fun onLoginCallback(result: SimpleAuthResult<Void>) {
        val builder = StringBuilder()
        builder.append(if (result.isSuccess) SimpleSession.getCurrentIdpType().name + " Login is succeed" else "FAIL / " + result.errorCode + " / " + result.errorMessaage)
        tokenTextView?.setText(builder.toString())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeToSignIn.setOnClickListener {
            Main.app.goToRoute(Routes.USER_SIGN_IN)
        }

        homeToSignUp.setOnClickListener {
            Main.app.goToRoute(Routes.USER_SIGN_UP)
        }

        homeGoogleLogin.setOnClickListener{
            login(
                this.activity!!, IdpType.GOOGLE
            ) { result -> onLoginCallback(result) }

        }

        homeGoogleLogout.setOnClickListener{
            SimpleSession.logout()
            this.tokenTextView?.text = "Logout!"

        }

        homeLogedIn.setOnClickListener{
            val builder = StringBuilder()
            builder.append(if (SimpleSession.isSignedIn(this.activity!!)) "Yes! Idp Type Is " + SimpleSession.getCurrentIdpType() else "No..")
            this.tokenTextView?.text = builder.toString()

        }

        homeIdpType.setOnClickListener{
            val idpType = SimpleSession.getCurrentIdpType()
            this.tokenTextView?.text = idpType?.name ?: "Null"

        }

        homeAccessToken.setOnClickListener{
            this.tokenTextView?.text = SimpleSession.getAccessToken()
            Log.i("SELECTED VALUE", "ID TOKEN : " +  this.tokenTextView?.text)
        }

        homeEmail.setOnClickListener{
            this.tokenTextView?.text = SimpleSession.getEmail()
        }

    }
}