package com.kapouter.raveled.search.yarns

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kapouter.api.model.Yarn
import com.kapouter.raveled.R
import com.kapouter.raveled.search.SearchEvent
import kotlinx.android.synthetic.main.fragment_search_yarns.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchYarnsFragment : Fragment() {

    lateinit var adapter: SearchYarnsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_yarns, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(activity)
        adapter = SearchYarnsAdapter()
        recycler.adapter = adapter

        val patterns = ArrayList<Yarn>()
        (0..20).mapTo(patterns) { Yarn("name" + it) }
        adapter.setItems(patterns)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onSearchEvent(event: SearchEvent) {
        Toast.makeText(activity, "yarn " + event.query, Toast.LENGTH_SHORT).show()
    }
}