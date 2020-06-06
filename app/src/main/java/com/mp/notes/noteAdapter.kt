package com.mp.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mp.notes.R
import com.mp.notes.note
import kotlinx.android.synthetic.main.notecard.view.*
import org.w3c.dom.Text

class NoteAdapter(private val cardlist: ArrayList<note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(cardView: View): RecyclerView.ViewHolder(cardView) {
        val editView: ImageView = cardView.edit
        val deleteView: ImageView = cardView.delete
        val title: TextView = cardView.title
        val desc: TextView = cardView.desc

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.notecard,
            parent, false)

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