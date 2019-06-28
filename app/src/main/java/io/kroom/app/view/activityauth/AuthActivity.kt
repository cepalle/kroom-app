package io.kroom.app.view.activityauth

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import io.kroom.app.R
import io.kroom.app.view.activityauth.usersignin.UserSignInFragment
import io.kroom.app.view.activityauth.usersignup.UserSignUpFragment
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    lateinit var model : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        model = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        if (savedInstanceState == null) {
            changeFragment(model.menuPosition)
        }

        authTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                changeFragment(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                if (tab.position != model.menuPosition) {
                    changeFragment(tab.position)
                }
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun changeFragment(position: Int) {
        model.menuPosition = position
        if (position == 0) supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, UserSignInFragment())
            .commit()
        else
            supportFragmentManager.beginTransaction()
                .replace(R.id.authFragmentContainer, UserSignUpFragment())
                .commit()
    }
}