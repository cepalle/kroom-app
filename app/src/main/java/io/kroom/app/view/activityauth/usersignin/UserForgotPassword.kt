package io.kroom.app.view.activityauth.usersignin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UserForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Forgot password"
        setContentView(io.kroom.app.R.layout.activity_forgot_password)

    }
}
