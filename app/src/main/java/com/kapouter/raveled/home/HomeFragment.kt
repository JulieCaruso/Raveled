package com.kapouter.raveled.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        private val LOG_TAG = HomeFragment::class.java.simpleName
    }

    lateinit var pagerAdapter: HomePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = HomePagerAdapter(childFragmentManager, requireContext())
        pager.adapter = pagerAdapter

        tabs.addTab(tabs.newTab().setText(R.string.projects))
        tabs.addTab(tabs.newTab().setText(R.string.queue))
        tabs.addTab(tabs.newTab().setText(R.string.favorites))
        tabs.setupWithViewPager(pager)
    }
}