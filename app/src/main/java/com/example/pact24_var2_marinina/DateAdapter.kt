package com.example.pact24_var2_marinina

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DateAdapter(private val dateList: List<DateItem>, private val itemClickListener: (Int) -> Unit)
    : RecyclerView.Adapter<DateAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        val moneyTextView: TextView = itemView.findViewById(R.id.moneyTextView)
        val zamTextView: TextView = itemView.findViewById(R.id.zametkiTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        init { itemView.setOnClickListener {
                itemClickListener(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date, parent, false)
        return ViewHolder(view) }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateItem = dateList[position]
        holder.typeTextView.text = dateItem.type
        holder.moneyTextView.text = dateItem.money
        holder.zamTextView.text = dateItem.zam
        holder.dateTextView.text =dateItem.date }
    override fun getItemCount(): Int = dateList.size
}

