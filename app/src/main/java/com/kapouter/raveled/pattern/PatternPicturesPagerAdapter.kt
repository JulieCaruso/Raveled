package com.kapouter.raveled.pattern

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.model.Photo
import com.kapouter.api.util.getScreenWidth
import com.kapouter.raveled.R
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.item_pattern_picture.view.*

class PatternPicturesPagerAdapter(val layoutInflater: LayoutInflater, val pictures: List<Photo>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater.inflate(R.layout.item_pattern_picture, container, false)
        Ion.with(itemView.picture)
                .resizeWidth(itemView.context.getScreenWidth())
                .fitCenter()
                .load(pictures.get(position).medium2_url)

        container.addView(itemView, 0)

        return itemView
    }

    override fun isViewFromObject(view: View, `object`: Any?): Boolean =
            view.equals(`object` as View)

    override fun getCount(): Int = pictures.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
        container.removeView(`object` as View)
    }
}