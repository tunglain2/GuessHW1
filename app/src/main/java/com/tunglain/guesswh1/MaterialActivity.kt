package com.tunglain.guesswh1

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*


class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD: Int = 100
    val secretNumber = SecretNumber()
    private val dialogOk = DialogInterface.OnClickListener { dialog, which ->
        secretNumber.reset()
        counter.setText(secretNumber.count.toString())
        ed_number.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            replay()
        }
        dataSet()
    }

    private fun dataSet() {
        logd(secretNumber.secret.toString())
        counter.setText(secretNumber.count.toString())
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER",-1)
        val nickname = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME",null)
        logd("count : ${count} / nickname: ${nickname}")
    }

    private fun replay() {
        with(AlertDialog.Builder(this)) {
            setTitle(getString(R.string.replay_game))
            setMessage(getString(R.string.are_you_sure))
            setPositiveButton(getString(R.string.ok), dialogOk)
            setNeutralButton(getString(R.string.cancel), null)
        }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK) {
                val nick = data?.getStringExtra("NICK")
                logd("onActivityResult : ${nick}")
                replay()
            }
        }
    }

    fun check(view: View) {
        if (ed_number.text.toString() != "")
        {
            val n = ed_number.text.toString().toInt()
            val diff = secretNumber.validate(n)
            var numberCount = secretNumber.count
            val message = when {
                diff < 0 -> getString(R.string.bigger)
                diff > 0 -> getString(R.string.smaller)
                diff == 0 && numberCount < 3 -> getString(R.string.excellent_the_number_is) + secretNumber.secret
                else -> getString(R.string.yes_you_got_it)
            }
            counter.setText(numberCount.toString())
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) {dialog, which ->
                    if (diff == 0) {
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("COUNTER", secretNumber.count)
//                    startActivity(intent)
                        startActivityForResult(intent, REQUEST_RECORD)
                    }
                }
                .show()
        }else {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.please_enter_a_number))
                .setPositiveButton(getString(R.string.ok),null)
                .show()
        }
    }

}
