package com.kapouter.raveled.home.projects

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kapouter.api.model.Project
import com.kapouter.raveled.R
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ProjectViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ProjectViewHolder
        holder.bind(items.get(position))
    }

    fun setItems(items: List<Project>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ProjectViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)) {
        fun bind(item: Project) = with(itemView) {
            name.text = item.name
            pattern_name.text = if (item.pattern_name != null) resources.getString(R.string.tiret, item.pattern_name) else ""
            progress.progress = item.progress
            Ion.with(picture)
                    .centerCrop()
                    .load(item.first_photo?.medium2_url)
        }
    }
}