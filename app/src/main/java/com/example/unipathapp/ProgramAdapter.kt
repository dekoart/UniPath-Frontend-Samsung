package com.example.unipathapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProgramAdapter(
    private val list: List<Program>
) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    class ProgramViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_program, parent, false)

        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProgramViewHolder,
        position: Int
    ) {

        val program = list[position]

        val title = holder.itemView.findViewById<TextView>(R.id.name)

        val university = holder.itemView.findViewById<TextView>(R.id.type)

        val city = holder.itemView.findViewById<TextView>(R.id.city)

        title.text = program.title
        university.text = program.university
        city.text = program.city
    }

    override fun getItemCount(): Int {

        return list.size
    }
}