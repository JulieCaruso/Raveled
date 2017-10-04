package com.kapouter.raveled.search

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.kapouter.raveled.R
import com.kapouter.raveled.search.patterns.SearchPatternsFragment
import com.kapouter.raveled.search.yarns.SearchYarnsFragment

class SearchPagerAdapter(fm: FragmentManager,
                         val context: Context) : FragmentStatePagerAdapter(fm) {

    companion object {
        const val PATTERNS = 0
        const val YARNS = 1
        const val PEOPLE = 2
        const val GROUPS = 3

        const val ITEM_NUMBER = 4
    }

    override fun getItem(position: Int): Fragment = when (position) {
        PATTERNS -> SearchPatternsFragment()
        YARNS -> SearchYarnsFragment()
        PEOPLE -> Fragment()
        GROUPS -> Fragment()
        else -> Fragment()
    }

    override fun getCount(): Int = ITEM_NUMBER

    override fun getPageTitle(position: Int): CharSequence {
        val resId: Int = when (position) {
            PATTERNS -> R.string.patterns
            YARNS -> R.string.yarns
            PEOPLE -> R.string.people
            GROUPS -> R.string.groups
            else -> R.string.patterns
        }
        return context.getString(resId)
    }
}