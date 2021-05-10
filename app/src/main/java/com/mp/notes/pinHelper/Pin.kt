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
import kotlinx.android.synthetic.main.content_pin_reset.*

class Pin : AppCompatActivity() {

    var numbers = ""
    var numbersConfirm = ""
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
                if (clickCounter == 4) {
                    if (confirmPin == 0) {
                        numbersConfirm = numbers
                        clearPinText()
                        btnOk.setText("Confirm")
                        clickCounter = 0
                        confirmPin++
                        numbers = ""
                    } else if(numbers == numbersConfirm){
                        clickCounter = 0
                        sharedPref.setPin(numbers.toInt())
                        pinEnabled = true
                        sharedPref.setAccess(true)
                        Toast.makeText(this, "Lock icon allows you to Enable or Disable using Pin", Toast.LENGTH_LONG).show()
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Pin does not match", Toast.LENGTH_LONG).show()
                        clearPinText()
                        btnOk.setText("OK")
                        confirmPin = 0
                        clickCounter = 0
                        numbersConfirm = ""
                        numbers = ""
                    }
                }
            }
            else{
                if(clickCounter == 4 && sharedPref.getPin() == numbers.toInt()){
                    numbers = ""
                    clickCounter = 0
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    clearPinText()
                    numbers = ""
                    clickCounter = 0
                    Toast.makeText(this, "Incorrect pin", Toast.LENGTH_LONG).show()
                }
            }
        }
        delete.setOnClickListener{
            clearPinText()
            numbers = ""
            clickCounter = 0
        }
    }

    fun NumberClicked(view: View) {
        val button = view as Button
        if(clickCounter == 0){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            pin1.setText("*")
            clickCounter++
        }
        else if(clickCounter == 1){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            pin2.setText("*")
            clickCounter++
        }
        else if(clickCounter == 2){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            pin3.setText("*")
            clickCounter++
        }
        else if(clickCounter == 3){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            pin4.setText("*")
            clickCounter++
        }
    }

    fun clearPinText(){
        pin1.setText("")
        pin2.setText("")
        pin3.setText("")
        pin4.setText("")
    }

}
