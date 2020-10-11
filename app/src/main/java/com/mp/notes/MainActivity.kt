package com.mp.notes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mp.notes.database.DatabaseHelper
import com.mp.notes.model.note
import com.mp.notes.pinHelper.Pin
import com.mp.notes.pinHelper.PinReset
import com.mp.notes.sharedPref.SharedPrefHandler
import com.mp.notesapp.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*

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

            var intent = Intent(this, AddNotes::class.java)
            intent.putExtra("edit", false)
            intent.putExtra("Name", "")
            intent.putExtra("Description", "")
            startActivity(intent)
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
//        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        when (item.itemId) {
//            R.id.addNote -> {
//                //Go to Add page
//                //Inter Process Communication
//                var intent = Intent(this, AddNotes::class.java)
//                intent.putExtra("edit", false)
//                intent.putExtra("Name", "")
//                intent.putExtra("Description", "")
//                startActivity(intent)
//            }
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
            R.id.pinSetting -> {
                val sharedPref = SharedPrefHandler(context)

                if(sharedPref.getPin() != 0 && sharedPref.getPin() != -1){
                    //pin is there
                    var intent = Intent(this, PinReset::class.java)
                    println("Called PIN RESET - Negative btn")
                    intent.putExtra("reset","true")
                    startActivity(intent)
                    finish()
                }
                else{
                    var intent = Intent(this, Pin::class.java)
                    println("Called PIN SETUP - Positive btn")
                    startActivity(intent)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
