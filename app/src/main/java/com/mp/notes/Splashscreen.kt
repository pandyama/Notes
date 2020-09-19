package com.mp.notes

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pin.toolbar
import kotlinx.android.synthetic.main.activity_splashscreen.*

class Splashscreen : AppCompatActivity() {

    private val PREF_NAME = "pin"
    val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        setSupportActionBar(toolbar)

        val sharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        var pin:Int = sharedPref.getInt("Pincode",0)



        if(pin.equals(0)){
            lateinit var dialog: AlertDialog
            // Initialize a new instance of alert dialog builder object
            val builder = AlertDialog.Builder(context)
            // Set a title for alert dialog
            builder.setTitle("Setup a pin Now or Later")
            // Set a message for alert dialog
            // On click listener for dialog buttons
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        var intent = Intent(this, Pin::class.java)
                        startActivity(intent)
                        finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        sharedPref.edit().putInt("Pincode",-1).commit()
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            builder.setPositiveButton("Now", dialogClickListener)
            // Set the alert dialog negative/no button
            builder.setNegativeButton("Later", dialogClickListener)
            // Initialize the AlertDialog using builder object
            dialog = builder.create()
            // Finally, display the alert dialog
            dialog.show()

        }
        else if(pin.equals(-1)){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            var intent = Intent(this, Pin::class.java)
            startActivity(intent)
            finish()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
