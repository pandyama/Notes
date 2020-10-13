package com.mp.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
            finish()
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
        var sharedPref = SharedPrefHandler(context)
        menuInflater.inflate(R.menu.main_menu, menu)

        if (menu != null && sharedPref.getAccess()) {
            menu.getItem(0).setIcon(R.drawable.pin_enable)
        }
        else if(menu != null && !sharedPref.getAccess()){
            menu.getItem(0).setIcon(R.drawable.pin_disable)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPref = SharedPrefHandler(context)
        when (item.itemId) {

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
            R.id.pinOnOff -> {
                if(sharedPref.getPin() != 0 && sharedPref.getPin() != -1 && !sharedPref.getAccess()){
                    //pin is there
                    //Switch Icon here and show toast saying pin is turned on
                    sharedPref.setAccess(true)
                    item.setIcon(R.drawable.pin_enable)
                    Toast.makeText(this, "Pin Enabled", Toast.LENGTH_LONG).show()

                }
                else if(sharedPref.getPin() != 0 && sharedPref.getPin() != -1 && sharedPref.getAccess()){
                    sharedPref.setAccess(false)
                    item.setIcon(R.drawable.pin_disable)
                    Toast.makeText(this, "Pin Disabled", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "Setup a Pin from settings menu in top right icon", Toast.LENGTH_LONG).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
