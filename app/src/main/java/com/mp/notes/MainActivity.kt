package com.mp.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mp.notesapp.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notes_add.*
import kotlinx.android.synthetic.main.notecard.*
import kotlinx.android.synthetic.main.notecard.view.*
import kotlinx.android.synthetic.main.notecard.view.delete

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<note>()
    val context = this

    var adapter = NoteAdapter(listNotes,context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//
//        listNotes.add(note("buy groceries","Lorem ipsum dolor sit " +
//                "amet, consectetur adipiscing elit, sed do eiusmod" +
//                " tempor incididunt ut labore et dolore magna aliqua. " +
//                "Ut enim ad minim veniam\""))
//        listNotes.add(note("get gas","go to Petro Canada"))
//        listNotes.add(note("buy gift","go to Erin Mills"))
//


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
        recycler_view.setHasFixedSize(true)


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
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    fun editNote() {
        var intent = Intent(this, AddNotes::class.java)
        startActivity(intent)

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
                startActivity(intent)

            }
            R.id.app_bar_search -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}
