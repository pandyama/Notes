package com.mp.notes

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mp.notes.database.DatabaseHelper
import com.mp.notes.model.note
import com.mp.notes.pinHelper.Pin
import com.mp.notes.pinHelper.PinReset
import com.mp.notes.sharedPref.SharedPrefHandler
import com.mp.notesapp.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<note>()
    val context = this
    var adapter = NoteAdapter(listNotes,context)
    var defaultLayout = 0
    lateinit var optionsMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPref = SharedPrefHandler(context)
        var DatabaseHelper = DatabaseHelper(this)
        val cursor = DatabaseHelper.getNotes()

        println("INSIDE MAIN ACTIVITY")
        println(cursor.count)

        if(cursor.count != 0){
            while (cursor.moveToNext()) {
                listNotes.add(note(cursor.getString(1), cursor.getString(2)))
            }
            recycler_view.adapter = adapter

            var pdfDocument = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                PdfDocument()
            } else {
                TODO("VERSION.SDK_INT < KITKAT")
            }
            if(sharedPref.getLayout() == "Linear"){
                recycler_view.layoutManager = LinearLayoutManager(this)
            }
            else{
                recycler_view.layoutManager = GridLayoutManager(this, 2)
            }
        }
        else{
            Toast.makeText(this, "Add Notes", Toast.LENGTH_LONG).show()
        }

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
        var sharedPref = SharedPrefHandler(context)

        listNotes.clear()

        while (cursor.moveToNext()) {
            listNotes.add(note(cursor.getString(1), cursor.getString(2)))
        }

        recycler_view.adapter = adapter
        if(sharedPref.getLayout() == "Linear"){
            recycler_view.layoutManager = LinearLayoutManager(this)
        }
        else{
            recycler_view.layoutManager = GridLayoutManager(this, 2)
        }
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //gets called automatically when activity fires up
        var sharedPref = SharedPrefHandler(context)
        menuInflater.inflate(R.menu.main_menu, menu)

        if (menu != null) {
            optionsMenu = menu
        }
//
//        if (menu != null && sharedPref.getAccess()) {
//            menu.getItem(0).setIcon(R.drawable.pin_enable)
//            optionsMenu.findItem(R.id.pinSetting).setTitle("Reset Pin")
//        }
//        else if(menu != null && !sharedPref.getAccess() && sharedPref.getPin() != -1 && sharedPref.getPin() != 999999){
//            menu.getItem(0).setIcon(R.drawable.pin_disable)
//            optionsMenu.findItem(R.id.pinSetting).setTitle("Reset Pin")
//        }
//        else if(menu != null && !sharedPref.getAccess() && sharedPref.getPin() == -1 ){
//            menu.getItem(0).setIcon(R.drawable.pin_disable)
//            optionsMenu.findItem(R.id.pinSetting).setTitle("Setup Pin")
//        }

        if(menu != null && sharedPref.getLayout() == "Linear"){
            menu.getItem(0).setTitle("Grid Layout")
        }
        else if(menu != null && sharedPref.getLayout() == "Grid"){
            menu.getItem(0).setTitle("Linear Layout")
        }


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPref = SharedPrefHandler(context)
        when (item.itemId) {
            R.id.layout -> {
                if(sharedPref.getLayout() == "Linear"){
                    recycler_view.layoutManager = GridLayoutManager(this,2)
                    item.setTitle("Linear Layout")
                    sharedPref.setLayout("Grid")
                    defaultLayout = 1
                }
                else{
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    item.setTitle("Grid Layout")
                    sharedPref.setLayout("Linear")
                    defaultLayout = 0
                }
            }
//            R.id.pinSetting -> {
//                if(sharedPref.getPin() != 999999 && sharedPref.getPin() != -1){
//                    //pin is there
//                    var intent = Intent(this, PinReset::class.java)
//                    intent.putExtra("reset","true")
//                    startActivity(intent)
//                    finish()
//                }
//                else{
//                    var intent = Intent(this, Pin::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//            R.id.pinOnOff -> {
//                if(sharedPref.getPin() != 999999 && sharedPref.getPin() != -1 && !sharedPref.getAccess()){
//                    //pin is there
//                    //Switch Icon here and show toast saying pin is turned on
//                    sharedPref.setAccess(true)
//                    item.setIcon(R.drawable.pin_enable)
//                    Toast.makeText(this, "Pin Enabled", Toast.LENGTH_LONG).show()
//                }
//                else if(sharedPref.getPin() != 999999 && sharedPref.getPin() != -1 && sharedPref.getAccess()){
//                    sharedPref.setAccess(false)
//                    item.setIcon(R.drawable.pin_disable)
//                    Toast.makeText(this, "Pin Disabled", Toast.LENGTH_LONG).show()
//                }
//                else{
//                    Toast.makeText(this, "Setup pin from settings menu at the top", Toast.LENGTH_LONG).show()
//                }
//            }
//            R.id.deletePin -> {
//                sharedPref.deletePin()
//                optionsMenu.findItem(R.id.pinOnOff).setIcon(R.drawable.pin_disable)
//                optionsMenu.findItem(R.id.pinSetting).setTitle("Setup Pin")
//                Toast.makeText(this, "Pin Deleted", Toast.LENGTH_LONG).show()
//            }
        }
        return super.onOptionsItemSelected(item)
    }
}
