package com.example.mychores.ui.chores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mychores.R
import com.example.mychores.model.Chore
import kotlinx.android.synthetic.main.item_chore.view.*

class ChoresAdapter(private val chores: List<Chore>): RecyclerView.Adapter<ChoresAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chore, parent, false))
    }

    override fun getItemCount(): Int = chores.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(chores[position])

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(chore: Chore) {
            itemView.run {
                tvChoreTitle.text = chore.choreName
                tvChoreDescription.text = chore.choreDescription
            }
        }
    }
}