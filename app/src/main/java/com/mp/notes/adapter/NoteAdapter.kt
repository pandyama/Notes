package com.mp.notesapp

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mp.notes.*
import com.mp.notes.database.DatabaseHelper
import com.mp.notes.model.note
import kotlinx.android.synthetic.main.notecard.view.*
import kotlinx.android.synthetic.main.notecard.view.delete

class NoteAdapter(private val cardlist: ArrayList<note>, val context: Context) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {
        //        val editView: ImageView = cardView.edit
//        val deleteView: ImageView = cardView.delete
        val title: TextView = cardView.title
        val desc: TextView = cardView.desc

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.notecard,
            parent, false
        )

//        var listNotes = ArrayList<note>()

        itemView.delete.setOnClickListener {

            lateinit var dialog: AlertDialog


            // Initialize a new instance of alert dialog builder object
            val builder = AlertDialog.Builder(context)

            // Set a title for alert dialog
            builder.setTitle("Are you sure you want to delete?")

            // Set a message for alert dialog


            // On click listener for dialog buttons
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val db = DatabaseHelper(context)
                        db.delete(itemView.title.text.toString(), itemView.desc.text.toString())
                        val cursor = db.getNotes()

                        cardlist.clear()


                        while (cursor.moveToNext()) {
                            println(cursor.getString(0))
                            println(cursor.getString(1))
                            println(cursor.getString(2))
                            cardlist.add(note(cursor.getString(1), cursor.getString(2)))
                        }
                        notifyDataSetChanged()
                    }
//                    DialogInterface.BUTTON_NEUTRAL -> Toast("Neutral/Cancel button clicked.")
                }
            }

            builder.setPositiveButton("YES", dialogClickListener)
            // Set the alert dialog negative/no button
            builder.setNegativeButton("NO", dialogClickListener)

            // Set the alert dialog neutral/cancel button


            // Initialize the AlertDialog using builder object
            dialog = builder.create()

            // Finally, display the alert dialog
            dialog.show()


        }

        itemView.edit.setOnClickListener {
            var intent = Intent(itemView.context, AddNotes::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("Name", itemView.title.text.toString())
            intent.putExtra("Description", itemView.desc.text.toString())
            itemView.context.startActivity(intent)
        }


        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = cardlist[position]

        holder.title.text = currentItem.noteName
        holder.desc.text = currentItem.noteDesc
    }

    override fun getItemCount(): Int {
        return cardlist.size
    }

}