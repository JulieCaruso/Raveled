package com.kapouter.raveled.search.yarns

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kapouter.api.model.Yarn
import com.kapouter.raveled.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_yarn.view.*

class SearchYarnsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Yarn>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = YarnViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as YarnViewHolder
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Yarn>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class YarnViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_yarn, parent, false)) {
        fun bind(item: Yarn) = with(itemView) {
            name.text = item.name
            company_name.text = item.yarn_company_name
            yarnWeight.text = item.yarn_weight?.name

            Picasso.get()
                    .load(item.first_photo?.medium2_url)
                    .placeholder(R.drawable.placeholder_yarn_ball)
                    .into(picture)
        }
    }
}