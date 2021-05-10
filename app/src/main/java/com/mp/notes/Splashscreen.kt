package com.mp.notes

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mp.notes.pinHelper.Pin
import com.mp.notes.sharedPref.SharedPrefHandler
import kotlinx.android.synthetic.main.activity_pin.toolbar

class Splashscreen : AppCompatActivity() {

    val context = this
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        setSupportActionBar(toolbar)

        val sharedPref = SharedPrefHandler(this)
        var pin:Int = sharedPref.getPin()
        var access = sharedPref.getAccess()


        if(pin.equals(999999) && !access){ //default is 999999
            println("INSIDE")
            println(sharedPref.getAccess())
            println(sharedPref.getPin())

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Setup a pin Now or Later")
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        var intent = Intent(this, Pin::class.java)
                        startActivity(intent)
                        finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        sharedPref.setPin(-1)
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            builder.setPositiveButton("Now", dialogClickListener)
            builder.setNegativeButton("Later", dialogClickListener)
            dialog = builder.create()
            dialog.show()
        }
        else if(pin.equals(-1) && !access){ // Chose not to set the pin
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(access){ // Pin got setup
            var intent = Intent(this, Pin::class.java)
            startActivity(intent)
            finish()
        }
        else{ // Pin
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onPause() {
        super.onPause()
        if(dialog != null) {
            dialog.hide()
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = SharedPrefHandler(this)
        var pin:Int = sharedPref.getPin()
        var access = sharedPref.getAccess()

        if(pin.equals(999999) && !access){ //default is 999999
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Setup a pin Now or Later")
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        var intent = Intent(this, Pin::class.java)
                        startActivity(intent)
                        finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        sharedPref.setPin(-1)
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            builder.setPositiveButton("Now", dialogClickListener)
            builder.setNegativeButton("Later", dialogClickListener)
            dialog = builder.create()
            dialog.show()
        }
        else if(pin.equals(-1) && !access){ // Chose not to set the pin
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(access && !pin.equals(-1) && !pin.equals(999999)){ // Pin got setup
            var intent = Intent(this, Pin::class.java)
            startActivity(intent)
            finish()
        }
        else{ // Pin
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
