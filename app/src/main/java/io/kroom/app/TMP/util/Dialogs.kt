package io.kroom.app.TMP.util

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


object Dialogs {

    fun errorDialog(activity: AppCompatActivity, message: String, title: String = "An error occured!"): AlertDialog.Builder {
        return AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("ok", null)
    }

    fun successDialog(activity: AppCompatActivity, message: String, title: String = "Success!"): AlertDialog.Builder {
        return AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("ok", null)
    }
}