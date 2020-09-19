package com.mp.notes

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mp.notesapp.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.notecard.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<note>()
    val context = this

    var adapter = NoteAdapter(listNotes,context)

    var defaultLayout = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var DatabaseHelper = DatabaseHelper(this)
        val cursor = DatabaseHelper.getNotes()

        while (cursor.moveToNext()) {
            println(cursor.getString(0))
            println(cursor.getString(1))
            println(cursor.getString(2))
            listNotes.add(note(cursor.getString(1), cursor.getString(2)))
        }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        LockBtnSetting.setOnClickListener{
            lateinit var dialog: AlertDialog
            // Initialize a new instance of alert dialog builder object
            val builder = AlertDialog.Builder(context)

            // Set a title for alert dialog
            builder.setTitle("Setup a pin or reset pin")
            // Set a message for alert dialog
            // On click listener for dialog buttons
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        var intent = Intent(this, Pin::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            builder.setPositiveButton("Setup", dialogClickListener)
            // Set the alert dialog negative/no button
            builder.setNegativeButton("Reset", dialogClickListener)
            // Initialize the AlertDialog using builder object
            dialog = builder.create()
            // Finally, display the alert dialog
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        var DatabaseHelper = DatabaseHelper(this)
        val cursor = DatabaseHelper.getNotes()

        listNotes.clear()

        while (cursor.moveToNext()) {
            println(cursor.getString(0))
            println(cursor.getString(1))
            println(cursor.getString(2))
            listNotes.add(note(cursor.getString(1), cursor.getString(2)))
        }

        recycler_view.adapter = adapter
        if(defaultLayout == 0){
            recycler_view.layoutManager = LinearLayoutManager(this)
        }
        else{
            recycler_view.layoutManager = GridLayoutManager(this,2)
        }
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //gets called automatically when activity fires up
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNote -> {
                //Go to Add page
                //Inter Process Communication
                var intent = Intent(this, AddNotes::class.java)
                intent.putExtra("edit", false)
                intent.putExtra("Name", "")
                intent.putExtra("Description", "")
                startActivity(intent)
            }
            R.id.layout -> {
                if(defaultLayout == 0){
                    recycler_view.layoutManager = GridLayoutManager(this,2)
                    item.setTitle("Linear Layout")
                    defaultLayout = 1
                }
                else{
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    item.setTitle("Grid Layout")
                    defaultLayout = 0
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
