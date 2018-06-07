package com.kapouter.raveled.search.patterns

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kapouter.api.model.Pattern
import com.kapouter.api.util.getScreenWidth
import com.kapouter.raveled.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pattern.view.*

class SearchPatternsAdapter(val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Pattern)
    }

    private val items = ArrayList<Pattern>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as PatternViewHolder
        holder.bind(items[position], onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = PatternViewHolder(parent)

    fun setItems(items: List<Pattern>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Pattern>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class PatternViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pattern, parent, false)) {
        fun bind(item: Pattern, onItemClickListener: OnItemClickListener) = with(itemView) {
            name.text = item.name
            author_name.text = resources.getString(R.string.tiret, item.pattern_author.name)
            Picasso.get()
                    .load(item.first_photo?.medium2_url)
                    .resize(itemView.context.getScreenWidth(), 0)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_yarn_ball)
                    .into(picture)

            itemView.setOnClickListener { onItemClickListener.onItemClick(item) }
        }
    }
}