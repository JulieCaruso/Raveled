package com.kapouter.raveled.home

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.kapouter.raveled.R
import com.kapouter.raveled.home.projects.ProjectsFragment
import com.kapouter.raveled.home.queue.QueueFragment

class HomePagerAdapter(fm: FragmentManager,
                       val context: Context) : FragmentStatePagerAdapter(fm) {

    companion object {
        const val PROJECTS = 0
        const val QUEUE = 1
        const val FAVORITES = 2

        const val ITEM_NUMBER = 3
    }

    override fun getCount(): Int = ITEM_NUMBER

    override fun getItem(position: Int): Fragment = when (position) {
        PROJECTS -> ProjectsFragment()
        QUEUE -> QueueFragment()
        FAVORITES -> Fragment()
        else -> Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence {
        val resId: Int = when (position) {
            PROJECTS -> R.string.projects
            QUEUE -> R.string.queue
            FAVORITES -> R.string.favorites
            else -> R.string.projects
        }
        return context.getString(resId)
    }
}