package com.mp.notes.sharedPref

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPrefHandler(context: Context) {

    private val PREF_NAME = "Pin"
    val preference = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun getPin(): Int {
        return preference.getInt("Pincode",0)
    }

    fun setPin(pin: Int){
        preference.edit().putInt("Pincode", pin).commit()
    }

    fun setAccess(access: Boolean){
        preference.edit().putBoolean("Access",access).commit()
    }
}
