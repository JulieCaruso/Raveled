package com.kapouter.raveled.search.filter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kapouter.raveled.R

class SortAdapter(val context: Context, val listener: OnItemSelectedListener) : RecyclerView.Adapter<SortAdapter.SortViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(item: FilterSort)
    }

    private val items = ArrayList<FilterSort>()
    private var selectedItem: FilterSort? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder = SortViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sort, parent, false))

    override fun getItemCount(): Int = items.size

    fun setSelectedItem(item: FilterSort) {
        selectedItem = item
        notifyDataSetChanged()
    }

    fun setItems(sorts: List<FilterSort>) {
        items.clear()
        items.addAll(sorts)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        val item = items[position]

        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, item.icon))
        holder.label.text = context.getString(item.label)
        holder.itemView.setOnClickListener {
            selectedItem = item
            listener.onItemSelected(item)
        }

        if (item == selectedItem) {
            holder.background.background = ContextCompat.getDrawable(context, R.drawable.bg_sort_item_selected)
            holder.label.setTextColor(Color.WHITE)
        } else {
            holder.background.background = ContextCompat.getDrawable(context, R.drawable.bg_sort_item)
            holder.label.setTextColor(Color.BLACK)
        }
    }

    class SortViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var background: View = view.findViewById(R.id.sort_background)
        var icon: ImageView = view.findViewById(R.id.sort_icon)
        var label: TextView = view.findViewById(R.id.sort_label)
    }
}