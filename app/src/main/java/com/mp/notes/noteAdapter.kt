package com.mp.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mp.notes.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.notecard.*
import kotlinx.android.synthetic.main.notecard.view.*
import kotlinx.android.synthetic.main.notecard.view.delete
import org.w3c.dom.Text

class NoteAdapter(private val cardlist: ArrayList<note>, val context: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(cardView: View): RecyclerView.ViewHolder(cardView) {
//        val editView: ImageView = cardView.edit
//        val deleteView: ImageView = cardView.delete
        val title: TextView = cardView.title
        val desc: TextView = cardView.desc

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.notecard,
            parent, false)

//        var listNotes = ArrayList<note>()

        itemView.delete.setOnClickListener{
            val db = DatabaseHelper(context)
            db.delete(itemView.title.text.toString(), itemView.desc.text.toString())
            val cursor = db.getNotes()

            cardlist.clear()


            while(cursor.moveToNext()){
                println(cursor.getString(0))
                println(cursor.getString(1))
                println(cursor.getString(2))
                cardlist.add(note(cursor.getString(1),cursor.getString(2)))
            }
            notifyDataSetChanged()
        }

        itemView.edit.setOnClickListener{
            var intent = Intent(itemView.context, AddNotes::class.java)
            intent.putExtra("edit",true)
            intent.putExtra("Name",itemView.title.text.toString())
            intent.putExtra("Description",itemView.desc.text.toString())
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