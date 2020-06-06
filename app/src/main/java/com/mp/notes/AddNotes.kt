package com.mp.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_add)
    }

    fun endActivity(view: View){
        finish()
    }
}
