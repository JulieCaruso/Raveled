package com.kapouter.raveled.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.*
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    lateinit var pagerAdapter: SearchPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {


        pagerAdapter = SearchPagerAdapter(childFragmentManager, activity)
        pager.adapter = pagerAdapter

        tabs.addTab(tabs.newTab().setText(R.string.patterns))
        tabs.addTab(tabs.newTab().setText(R.string.yarns))
        tabs.addTab(tabs.newTab().setText(R.string.people))
        tabs.addTab(tabs.newTab().setText(R.string.groups))
        tabs.setupWithViewPager(pager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView?
        searchView?.setIconifiedByDefault(false)
        searchView?.maxWidth = Integer.MAX_VALUE

    }
}
