package com.kapouter.raveled.search.patterns

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kapouter.api.model.Pattern
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.layout_item_pattern.view.*

class SearchPatternsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Pattern>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as PatternViewHolder
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = PatternViewHolder(parent)

    fun setItems(items: List<Pattern>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class PatternViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_pattern, parent, false)) {
        fun bind(item: Pattern) = with(itemView) {
            name.text = item.name
        }
    }
}