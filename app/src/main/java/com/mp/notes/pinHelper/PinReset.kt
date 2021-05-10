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
import kotlinx.android.synthetic.main.activity_pin_reset.*
import kotlinx.android.synthetic.main.content_pin.*
import kotlinx.android.synthetic.main.content_pin_reset.*
import kotlinx.android.synthetic.main.content_pin_reset.rOldPinText

class PinReset : AppCompatActivity() {
    var numbers = ""
    var numbersConfirm = ""
    var NumberClicked = ""
    var clickCounter = 0
    var confirmPin = 0
    var newPin = 0
    var pinEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_reset)
        setSupportActionBar(toolbar)

        val sharedPref = SharedPrefHandler(this)
        var currentPin = sharedPref.getPin()

        rbtnOk.setOnClickListener{
            if((numbers.toInt() == currentPin) && (newPin == 0)){
                numbers = ""
                clickCounter = 0
                newPin = 1
                clearPinText()
                rOldPinText.setText("Enter New Pin")
            }
            else if(newPin == 1){
                if (clickCounter == 4) {
                    if (confirmPin == 0) {
                        numbersConfirm = numbers
                        clearPinText()
                        rbtnOk.setText("Confirm")
                        clickCounter = 0
                        confirmPin++
                        numbers = ""
                    } else if(numbers == numbersConfirm){
                        clickCounter = 0
                        sharedPref.setPin(numbers.toInt())
                        pinEnabled = true
                        sharedPref.setAccess(true)
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else{
                        Toast.makeText(this, "Pin does not match", Toast.LENGTH_LONG).show()
                        clearPinText()
                        rbtnOk.setText("OK")
                        confirmPin = 0
                        clickCounter = 0
                        numbersConfirm = ""
                        numbers = ""
                    }
                }
            }
            else{
                numbers = ""
                clickCounter = 0
                Toast.makeText(this, "Incorrect pin", Toast.LENGTH_LONG).show()
                clearPinText()
            }
        }
        rdelete.setOnClickListener{
            clearPinText()
            numbers = ""
            clickCounter=0
        }
    }

    fun NumberClicked(view: View) {
        val button = view as Button
        if(clickCounter == 0){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            rpin1.setText("*")
            clickCounter++
        }
        else if(clickCounter == 1){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            rpin2.setText("*")
            clickCounter++
        }
        else if(clickCounter == 2){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            rpin3.setText("*")
            clickCounter++
        }
        else if(clickCounter == 3){
            NumberClicked = button.text.toString()
            numbers = numbers + NumberClicked
            rpin4.setText("*")
            clickCounter++
        }
    }

    fun clearPinText(){
        rpin1.setText("")
        rpin2.setText("")
        rpin3.setText("")
        rpin4.setText("")
    }

}
