package com.kapouter.raveled.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.*
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.fragment_search.*
import org.greenrobot.eventbus.EventBus


class SearchFragment : Fragment() {

    private lateinit var pagerAdapter: SearchPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = SearchPagerAdapter(childFragmentManager, requireContext())
        pager.adapter = pagerAdapter
        pager.offscreenPageLimit = 3

        tabs.addTab(tabs.newTab().setText(R.string.patterns))
        tabs.addTab(tabs.newTab().setText(R.string.yarns))
        tabs.addTab(tabs.newTab().setText(R.string.people))
        tabs.addTab(tabs.newTab().setText(R.string.groups))
        tabs.setupWithViewPager(pager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView?
        searchView?.maxWidth = Integer.MAX_VALUE
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                EventBus.getDefault().post(SearchEvent(query))
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean = true
        })
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                EventBus.getDefault().post(SearchEvent(null))
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                R.id.filter -> {
                    EventBus.getDefault().postSticky(FilterEvent(pagerAdapter.getPageTitle(pager.currentItem).toString()))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
