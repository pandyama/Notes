package com.mp.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.mp.notesapp.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        listNotes.add(note("buy groceries","Lorem ipsum dolor sit " +
                "amet, consectetur adipiscing elit, sed do eiusmod" +
                " tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam\""))
        listNotes.add(note("get gas","go to Petro Canada"))
        listNotes.add(note("buy gift","go to Erin Mills"))


        recycler_view.adapter = NoteAdapter(listNotes)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //gets called automatically when activity fires up

        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addNote ->{
                //Go to Add page
                //Inter Process Communication

                var intent = Intent(this,AddNotes::class.java)
                startActivity(intent)

            }
            R.id.app_bar_search -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}
