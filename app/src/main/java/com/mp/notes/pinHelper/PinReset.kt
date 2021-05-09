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
import kotlinx.android.synthetic.main.content_pin_reset.*

class PinReset : AppCompatActivity() {

    var numbers = ""
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
                rpin1.setText("")
                rpin2.setText("")
                rpin3.setText("")
                rpin4.setText("")
                rOldPinText.setText("Enter New Pin")
            }
            else if(newPin == 1){
                if (clickCounter == 3) {
                    println("..click counter is 3")
                    if (confirmPin == 0) {
                        rpin1.setText("")
                        rpin2.setText("")
                        rpin3.setText("")
                        rpin4.setText("")
                        rbtnOk.setText("Confirm")
                        clickCounter = 0
                        confirmPin++
                        numbers = ""
                    } else {
                        clickCounter = 0
                        sharedPref.setPin(numbers.toInt())
                        pinEnabled = true
                        sharedPref.setAccess(true)
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            else{
                numbers = ""
                clickCounter = 0
                Toast.makeText(this, "Incorrect pin", Toast.LENGTH_LONG).show()
                rpin1.setText("")
                rpin2.setText("")
                rpin3.setText("")
                rpin4.setText("")
            }
        }


        rdelete.setOnClickListener{
            if(rpin4.text.toString() != ""){
                rpin4.setText("")
                numbers = numbers.dropLast(1)
                clickCounter = 3
            }
            else if(rpin3.text.toString() != ""){
                rpin3.setText("")
                numbers = numbers.dropLast(1)
                clickCounter = 2
            }
            else if(rpin2.text.toString() != ""){
                rpin2.setText("")
                numbers = numbers.dropLast(1)
                clickCounter = 1
            }
            else{
                rpin1.setText("")
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
            rpin1.setText("*")
            clickCounter++
        }
        else if(clickCounter == 1){
            rpin2.setText("*")
            clickCounter++
        }
        else if(clickCounter == 2){
            rpin3.setText("*")
            clickCounter++
        }
        else{
            rpin4.setText("*")
        }
    }

}
