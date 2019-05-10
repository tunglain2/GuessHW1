package com.tunglain.guesswh1

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    val dialogOkListener = DialogInterface.OnClickListener{ dialog, which ->
        secretNumber.reset()
        counter.setText(secretNumber.count.toString())
        ed_number.setText("")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        logd(secretNumber.secret.toString())

        fab.setOnClickListener { view ->
            with(AlertDialog.Builder(this)){
                setTitle(getString(R.string.replay_game))
                setMessage(getString(R.string.are_you_sure))
                setPositiveButton(getString(R.string.ok),dialogOkListener)
                setNeutralButton(getString(R.string.cancel),null)
            }.show()
        }
        counter.setText(secretNumber.count.toString())
    }

    fun check(view : View) {
        if (ed_number.text.toString() != "") {
            val n = ed_number.text.toString().toInt()
            val diff = secretNumber.validate(n)
            val numberCount = secretNumber.count
            val message = when {
                diff < 0 -> getString(R.string.bigger)
                diff > 0 -> getString(R.string.smaller)
                diff == 0 && numberCount < 3 -> getString(R.string.excellent_the_number_is) + n.toString()
                else -> getString(R.string.yes_you_got_it)
            }
            counter.setText(secretNumber.count.toString())
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok),null)
                .show()
        } else {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.please_enter_a_number_1_10))
                .setPositiveButton(getString(R.string.ok),null)
                .show()
        }
    }
}
