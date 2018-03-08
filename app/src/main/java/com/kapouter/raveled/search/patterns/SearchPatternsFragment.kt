package com.kapouter.raveled.search.patterns

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import com.kapouter.raveled.search.SearchEvent
import kotlinx.android.synthetic.main.fragment_search_patterns.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchPatternsFragment : Fragment() {

    companion object {
        private val LOG_TAG = SearchPatternsFragment::class.java.simpleName
    }

    lateinit var adapter: SearchPatternsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_patterns, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(activity)
        adapter = SearchPatternsAdapter(resources.displayMetrics.widthPixels)
        recycler.adapter = adapter

        App.api.getPatterns(null)
                .compose(SchedulerTransformer())
                .subscribe(
                        { response ->
                            adapter.setItems(response.patterns)
                        },
                        { e -> Log.e(LOG_TAG, e.toString()) }
                )
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
        App.api.getPatterns(event.query)
                .compose(SchedulerTransformer())
                .subscribe(
                        { response ->
                            adapter.setItems(response.patterns)
                        },
                        { e -> Log.e(LOG_TAG, e.toString()) }
                )
    }
}