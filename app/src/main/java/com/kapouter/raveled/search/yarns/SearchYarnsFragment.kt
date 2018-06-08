package com.kapouter.raveled.search.yarns

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import com.kapouter.raveled.search.EndlessRecyclerViewListener
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
    lateinit var infiniteScroll: EndlessRecyclerViewListener
    var query: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_yarns, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        recycler.layoutManager = layoutManager
        adapter = SearchYarnsAdapter()
        recycler.adapter = adapter
        infiniteScroll = object : EndlessRecyclerViewListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                getData(page)
            }
        }
        recycler.addOnScrollListener(infiniteScroll)

        getData()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun getData(page: Int = 1) {
        if (page == 1) {
            infiniteScroll.resetState()
            adapter.setItems(listOf())
            loader.visibility = View.VISIBLE
        } else {
            infinite_loader.visibility = View.VISIBLE
        }
        App.api.getYarns(query, page)
                .compose(SchedulerTransformer())
                .subscribe(
                        { response ->
                            if (page == 1) {
                                adapter.setItems(response.yarns)
                                recycler.scrollToPosition(0)
                                loader.visibility = View.GONE
                            } else {
                                adapter.addItems(response.yarns)
                                infinite_loader.visibility = View.GONE
                            }
                        },
                        { e -> Log.e(LOG_TAG, e.toString()) }
                )
    }

    @Subscribe
    fun onSearchEvent(event: SearchEvent) {
        query = event.query
        getData()
    }

    @Subscribe
    fun onFilterEvent(event: FilterEvent) {

    }
}