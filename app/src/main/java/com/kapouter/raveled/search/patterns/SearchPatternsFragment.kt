package com.kapouter.raveled.search.patterns

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.model.Pattern
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import com.kapouter.raveled.pattern.PatternActivity
import com.kapouter.raveled.search.EndlessRecyclerViewListener
import com.kapouter.raveled.search.FilterEvent
import com.kapouter.raveled.search.SearchEvent
import com.kapouter.raveled.search.filter.Filter
import com.kapouter.raveled.search.filter.FilterActivity
import com.kapouter.raveled.search.filter.FilterActivity.Companion.EXTRA_FILTER
import com.kapouter.raveled.search.filter.getQuery
import com.kapouter.raveled.search.filter.getQueryWithMM
import kotlinx.android.synthetic.main.fragment_search_patterns.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchPatternsFragment : Fragment() {

    companion object {
        private val LOG_TAG = SearchPatternsFragment::class.java.simpleName
        private val FILTER_REQUEST = 10
    }

    lateinit var adapter: SearchPatternsAdapter
    lateinit var infiniteScroll: EndlessRecyclerViewListener
    private var query: String? = null
    private var filters: Filter? = Filter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_patterns, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recycler.layoutManager = layoutManager
        adapter = SearchPatternsAdapter(object : SearchPatternsAdapter.OnItemClickListener {
            override fun onItemClick(item: Pattern) {
                startActivity(PatternActivity.createIntent(requireContext(), item.id))
            }
        })
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
        App.api.getPatterns(query,
                filters?.sort?.value,
                filters?.craft?.getQuery(),
                filters?.category?.getQuery(),
                filters?.meterage?.getQuery(),
                filters?.colors,
                filters?.needleSize?.getQueryWithMM(),
                page)
                .compose(SchedulerTransformer())
                .subscribe(
                        { response ->
                            if (page == 1) {
                                adapter.setItems(response.patterns)
                                recycler.scrollToPosition(0)
                                loader.visibility = View.GONE
                            } else {
                                adapter.addItems(response.patterns)
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
        if (event.pageTitle == getString(R.string.patterns))
            startActivityForResult(FilterActivity.createIntent(requireContext(), filters), FILTER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILTER_REQUEST && resultCode == Activity.RESULT_OK) {
            filters = data?.getParcelableExtra(EXTRA_FILTER)
            getData()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}