package io.kroom.app

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import io.kroom.app.client.KroomClient
import kotlinx.android.synthetic.main.activity_main.*
import com.jk.simple.idp.IdpType
import com.jk.simple.SimpleAuthResult
import com.jk.simple.SimpleAuthResultCallback
import com.jk.simple.SimpleSession
import org.jetbrains.annotations.Nullable


class MainActivity : AppCompatActivity() {

    private var tokenTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        tokenTextView = findViewById(R.id.tokenView) as TextView

       // SimpleSession.setAuthProvider(IdpType.GOOGLE,  "795222071121-ugorkmpng7smt6v8akb25s7n08hqp826.apps.googleusercontent.com")
        //SimpleSession.setAuthProvider(IdpType.GOOGLE,  "795222071121-r8frsfild7amcomj2c9hb6qn682mjchj.apps.googleusercontent.com")
         //SimpleSession.setAuthProvider(IdpType.GOOGLE,  "795222071121-uak962mcfle3pv0pd9vinkpu9l5ri740.apps.googleusercontent.com")
        SimpleSession.setAuthProvider(IdpType.GOOGLE,  "795222071121-p1fbtb3mv3cjo9priggduc335rd8ng4d.apps.googleusercontent.com")

        buttonSuperYolo.setOnClickListener {
            KroomClient.trackById(3135556)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        SimpleSession.onActivityResult(requestCode, resultCode, data)
    }
    fun onGoogleLoginClick(v: View) {
        SimpleSession.login(
            this, IdpType.GOOGLE
        ) { result -> onLoginCallback(result) }

    }

    private fun onLoginCallback(result: SimpleAuthResult<Void>) {
        val builder = StringBuilder()
        builder.append(if (result.isSuccess) SimpleSession.getCurrentIdpType().name + " Login is succeed" else "FAIL / " + result.errorCode + " / " + result.errorMessaage)
        tokenTextView?.setText(builder.toString())
    }

    fun onLogoutClick(v: View) {
        SimpleSession.logout()
        tokenTextView?.setText("Logout!")
    }

    fun onlogedInClick(v: View) {
        val builder = StringBuilder()
        builder.append(if (SimpleSession.isSignedIn(this)) "Yes! Idp Type Is " + SimpleSession.getCurrentIdpType() else "No..")
        this.tokenTextView?.setText(builder.toString())
    }
    //.setText(builder.toString())

    fun onGetIdpType(v: View) {
        val idpType = SimpleSession.getCurrentIdpType()
        this.tokenTextView?.setText(idpType?.name ?: "Null")
    }

    fun onGetAccessTokenClick(v: View) {
        this.tokenTextView?.setText(SimpleSession.getAccessToken())
    }

    fun onGetEmailClick(v: View) {
        this.tokenTextView?.setText(SimpleSession.getEmail())
    }


}
