package com.kapouter.raveled.search.patterns

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kapouter.api.model.Pattern
import com.kapouter.raveled.R
import com.kapouter.raveled.search.SearchEvent
import kotlinx.android.synthetic.main.fragment_search_patterns.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchPatternsFragment : Fragment() {

    lateinit var adapter: SearchPatternsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_patterns, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(activity)
        adapter = SearchPatternsAdapter()
        recycler.adapter = adapter

        val patterns = ArrayList<Pattern>()
        (0..20).mapTo(patterns) { Pattern("name" + it) }

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
        Toast.makeText(activity, event.query, Toast.LENGTH_SHORT).show()
    }
}