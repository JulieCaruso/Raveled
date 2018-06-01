package com.kapouter.raveled.search.yarns

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
import com.kapouter.raveled.search.FilterEvent
import com.kapouter.raveled.search.SearchEvent
import kotlinx.android.synthetic.main.fragment_search_yarns.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchYarnsFragment : Fragment() {

    companion object {
        private val LOG_TAG = SearchYarnsFragment::class.java.simpleName
    }

    lateinit var adapter: SearchYarnsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_yarns, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(activity)
        adapter = SearchYarnsAdapter()
        recycler.adapter = adapter

        App.api.getYarns("")
                .compose(SchedulerTransformer())
                .subscribe(
                        { response -> adapter.setItems(response.yarns) },
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
        loader.visibility = View.VISIBLE
        App.api.getYarns(event.query)
                .compose(SchedulerTransformer())
                .subscribe(
                        { response ->
                            adapter.setItems(response.yarns)
                            recycler.scrollToPosition(0)
                            loader.visibility = View.GONE
                        },
                        { e -> Log.e(LOG_TAG, e.toString()) }
                )
    }

    @Subscribe
    fun onFilterEvent(event: FilterEvent) {

    }
}