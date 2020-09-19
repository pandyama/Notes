package com.mp.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.activity_pin.*
import kotlinx.android.synthetic.main.content_pin.*

class Pin : AppCompatActivity() {

//    var numbers: MutableList<String> = arrayListOf<String>()
    var numbers = ""
    var NumberClicked = ""
    var clickCounter = 0
    var confirmPin = 0

    private val PREF_NAME = "pin"
    var pinEnabled = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        setSupportActionBar(toolbar)
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        //println("pincode is "+sharedPref.getInt("Pincode",0))

        if(sharedPref.getInt("Pincode",0) != 0 && sharedPref.getInt("Pincode",0) != -1){
            pinEnabled = true
        }

        btnOk.setOnClickListener{
            if(!pinEnabled) {
                println(".Pin is not enabled")
                if (clickCounter == 3) {
                    println("..click counter is 3")
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
                        println("...pin confirmed, sending to sharedpref")
                        clickCounter = 0
                        sharedPref.edit().putInt("Pincode",numbers.toInt()).commit()
                        pinEnabled = true
                        var intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra("edit", false)
//                    intent.putExtra("Name", "")
//                    intent.putExtra("Description", "")
                        startActivity(intent)
                    }
                }
//                pinEnabled = true
            }
            else{
                println("Entered pin is "+numbers)
                println(sharedPref.getInt("Pincode", 0))
                if(clickCounter == 3 && sharedPref.getInt("Pincode", 0) == numbers.toInt()){
                    println("clickCounter is "+clickCounter)
                    var intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra("edit", false)
//                    intent.putExtra("Name", "")
//                    intent.putExtra("Description", "")
                    startActivity(intent)
                    numbers = ""
                    clickCounter = 0
                    sharedPref.edit().putBoolean("Access",true).commit()
                }
                else{
                    pin1.setText("")
                    pin2.setText("")
                    pin3.setText("")
                    pin4.setText("")
                    numbers = ""
                    clickCounter = 0
                }

            }

        }

        delete.setOnClickListener{
            if(pin4.text.toString() != ""){
                pin4.setText("")
                clickCounter = 3
            }
            else if(pin3.text.toString() != ""){
                pin3.setText("")
                clickCounter = 2
            }
            else if(pin2.text.toString() != ""){
                pin2.setText("")
                clickCounter = 1
            }
            else{
                pin1.setText("")
                clickCounter = 0
            }

        }
    }

    override fun onRestart() {
        super.onRestart()
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        btnOk.setOnClickListener{
            if(!pinEnabled) {
                println(".Pin is not enabled")
                if (clickCounter == 3) {
                    println("..click counter is 3")
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
                        println("...pin confirmed, sending to sharedpref")
                        clickCounter = 0
                        sharedPref.edit().putInt("Pincode",numbers.toInt()).commit()
                        pinEnabled = true
                        var intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra("edit", false)
//                    intent.putExtra("Name", "")
//                    intent.putExtra("Description", "")
                        startActivity(intent)
                    }
                }
//                pinEnabled = true
            }
            else{
                println("Entered pin is "+numbers)
                println(sharedPref.getInt("Pincode", 0))
                if(clickCounter == 3 && sharedPref.getInt("Pincode", 0) == numbers.toInt()){
                    var intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra("edit", false)
//                    intent.putExtra("Name", "")
//                    intent.putExtra("Description", "")
                    startActivity(intent)
                    numbers = ""
                    clickCounter = 0
                }
                else{
                    pin1.setText("")
                    pin2.setText("")
                    pin3.setText("")
                    pin4.setText("")
                    numbers = ""
                    clickCounter = 0
                }
            }
        }
    }

    fun NumberClicked(view: View) {
        val button = view as Button
        NumberClicked = button.text.toString()
        numbers = numbers + NumberClicked
        if(clickCounter == 0){
            pin1.setText(NumberClicked)
            clickCounter++
        }
        else if(clickCounter == 1){
            pin2.setText(NumberClicked)
            clickCounter++
        }
        else if(clickCounter == 2){
            pin3.setText(NumberClicked)
            clickCounter++
        }
        else{
            pin4.setText(NumberClicked)
        }

        println("Number clicked is "+NumberClicked)
    }

}
