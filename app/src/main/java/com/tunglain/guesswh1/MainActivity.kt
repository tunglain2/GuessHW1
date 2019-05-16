package com.tunglain.guesswh1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logd(secretNumber.secret.toString())
    }

    fun check(view : View) {
        if (ed_number.text.toString() != "") {
            val n = ed_number.text.toString().toInt()
            val diff = secretNumber.validate(n)
            val numberCount = secretNumber.count
            val message = when {
                diff < 0 -> getString(R.string.bigger)
                diff > 0 -> getString(R.string.smaller)
                diff == 0 && diff < 3 -> getString(R.string.excellent_the_number_is) + n.toString()
                else -> getString(R.string.yes_you_got_it)
            }
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok),null)
                .show()
        } else {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.please_enter_a_number))
                .setPositiveButton(getString(R.string.ok),null)
                .show()
        }
    }
}



fun Activity.logd(message : String) {
    if (BuildConfig.DEBUG)
    Log.d(this::class.java.simpleName,message);
}
