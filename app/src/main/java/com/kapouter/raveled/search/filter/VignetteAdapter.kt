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

class VignetteAdapter(private val context: Context, private val isMultiple: Boolean, private val listener: OnItemSelectedListener) : RecyclerView.Adapter<VignetteAdapter.SortViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(item: FilterItem, selectedItems: List<FilterItem>)
    }

    private val items = ArrayList<FilterItem>()
    private var selectedItems: ArrayList<FilterItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder = SortViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sort, parent, false))

    override fun getItemCount(): Int = items.size

    private fun setSelectedItem(item: FilterItem) {
        if (!isMultiple) {
            selectedItems.clear()
            selectedItems.add(item)
        } else if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
        notifyDataSetChanged()
    }

    fun setSelectedItems(items: List<FilterItem>) {
        selectedItems.clear()
        selectedItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setItems(sorts: List<FilterItem>) {
        items.clear()
        items.addAll(sorts)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        val item = items[position]

        if (item.icon != null) {
            holder.icon.visibility = View.VISIBLE
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, item.icon))
        } else
            holder.icon.visibility = View.GONE
        holder.label.text = context.getString(item.label)
        holder.itemView.setOnClickListener {
            setSelectedItem(item)
            listener.onItemSelected(item, selectedItems)
        }

        if (selectedItems.contains(item)) {
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