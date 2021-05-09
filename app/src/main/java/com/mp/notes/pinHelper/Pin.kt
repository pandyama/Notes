package com.mp.notes.pinHelper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mp.notes.MainActivity
import com.mp.notes.R
import com.mp.notes.sharedPref.SharedPrefHandler
import kotlinx.android.synthetic.main.activity_pin.*
import kotlinx.android.synthetic.main.content_pin.*

class Pin : AppCompatActivity() {

    var numbers = ""
    var NumberClicked = ""
    var clickCounter = 0
    var confirmPin = 0

    var pinEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        setSupportActionBar(toolbar)

        val sharedPref = SharedPrefHandler(this)

        if(sharedPref.getPin() != 999999 && sharedPref.getPin() != -1){
            pinEnabled = true
        }

        btnOk.setOnClickListener{
            if(!pinEnabled) {
                if (clickCounter == 3) {
                    if (confirmPin == 0) {
                        pin1.setText("")
                        pin2.setText("")
                        pin3.setText("")
                        pin4.setText("")
                        btnOk.setText("Confirm")
                        clickCounter = 0
                        confirmPin++
                        numbers = ""
                    } else {
                        clickCounter = 0
                        sharedPref.setPin(numbers.toInt())
                        pinEnabled = true
                        sharedPref.setAccess(true)
                        Toast.makeText(this, "Lock icon allows you to Enable or Disable using Pin", Toast.LENGTH_LONG).show()
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            else{
                if(clickCounter == 3 && sharedPref.getPin() == numbers.toInt()){
                    numbers = ""
                    clickCounter = 0
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    pin1.setText("")
                    pin2.setText("")
                    pin3.setText("")
                    pin4.setText("")
                    numbers = ""
                    clickCounter = 0
                    Toast.makeText(this, "Incorrect pin", Toast.LENGTH_LONG).show()
                }

            }

        }

        delete.setOnClickListener{
            if(pin4.text.toString() != ""){
                pin4.setText("")
                numbers = numbers.dropLast(1)
                clickCounter = 3
            }
            else if(pin3.text.toString() != ""){
                pin3.setText("")
                numbers = numbers.dropLast(1)
                clickCounter = 2
            }
            else if(pin2.text.toString() != ""){
                pin2.setText("")
                numbers = numbers.dropLast(1)
                clickCounter = 1
            }
            else{
                pin1.setText("")
                numbers = numbers.dropLast(1)
                clickCounter = 0
            }
        }
    }

    fun NumberClicked(view: View) {
        val button = view as Button
        NumberClicked = button.text.toString()
        numbers = numbers + NumberClicked
        if(clickCounter == 0){
            pin1.setText("*")
            clickCounter++
        }
        else if(clickCounter == 1){
            pin2.setText("*")
            clickCounter++
        }
        else if(clickCounter == 2){
            pin3.setText("*")
            clickCounter++
        }
        else{
            pin4.setText("*")
        }
    }

}
