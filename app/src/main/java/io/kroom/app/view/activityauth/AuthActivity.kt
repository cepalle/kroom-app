package io.kroom.app.view.activityauth

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

        authTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                changeFragment(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                if (tab.position != model.menuPosition) {
                    changeFragment(tab)
                }
            }
        })
    }

    private fun changeFragment(tab: TabLayout.Tab) {
        model.menuPosition = tab.position
        if (tab.position == 0) supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, UserSignInFragment())
            .commit()
        else
            supportFragmentManager.beginTransaction()
                .replace(R.id.authFragmentContainer, UserSignUpFragment())
                .commit()
    }
}