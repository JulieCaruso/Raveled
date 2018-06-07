package com.kapouter.raveled.home.queue

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.model.QueueProject
import com.kapouter.api.util.getScreenWidth
import com.kapouter.raveled.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_queue_project.view.*

class QueueAdapter : RecyclerView.Adapter<QueueAdapter.QueueProjectViewHolder>() {

    private val items = ArrayList<QueueProject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueProjectViewHolder = QueueProjectViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: QueueProjectViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<QueueProject>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class QueueProjectViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_queue_project, parent, false)) {
        fun bind(item: QueueProject) = with(itemView) {
            name.text = item.short_pattern_name
            author_name.text = resources.getString(R.string.tiret, item.pattern_author_name)
            if (item.yarn_name.isEmpty()) {
                use_yarn_layout.visibility = View.GONE
            } else {
                use_yarn_layout.visibility = View.VISIBLE
                use_yarn.text = item.yarn_name
            }
            if (item.notes.isEmpty()) {
                notes_layout.visibility = View.GONE
            } else {
                notes_layout.visibility = View.VISIBLE
                notes.text = item.notes
            }
            Picasso.get()
                    .load(item.best_photo?.medium2_url)
                    .resize(itemView.context.getScreenWidth(), 0)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_yarn_ball)
                    .into(picture)
        }
    }
}