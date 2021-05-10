package com.mp.notes

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mp.notes.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_notes_add.*

class AddNotes : AppCompatActivity() {

    var edit = false
    var oldName = ""
    var oldDesc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_add)

        var bundle = intent.extras

        edit = bundle!!.getBoolean("edit")
        if(edit) {
            noteName.setText(bundle!!.getString("Name").toString())
            noteDesc.setText(bundle!!.getString("Description").toString())
            oldName = noteName.text.toString()
            oldDesc = noteDesc.text.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        var bundle = intent.extras

        edit = bundle!!.getBoolean("edit")
        if(edit) {
            noteName.setText(bundle!!.getString("Name").toString())
            noteDesc.setText(bundle!!.getString("Description").toString())
            oldName = noteName.text.toString()
            oldDesc = noteDesc.text.toString()
        }
    }

    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

    fun endActivity(view: View){

        if(noteName.text.toString() != "" && noteDesc.text.toString() != "") {
            var dbManager = DatabaseHelper(this)
            var values = ContentValues()
            values.put("Name", noteName.text.toString())
            values.put("Description", noteDesc.text.toString())

            if (edit) {
                var id = dbManager.getID(oldName, oldDesc)
                dbManager.update(id, noteName.text.toString(), noteDesc.text.toString())
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val ID = dbManager.insert(noteName.text.toString(), noteDesc.text.toString())
                if (ID > 0) {
                    Toast.makeText(this, "Note created successfully", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed to save Note", Toast.LENGTH_LONG).show()
                }
            }
        }
        else{
            Toast.makeText(this, "Title and details must be filled", Toast.LENGTH_LONG).show()
        }
    }
}
