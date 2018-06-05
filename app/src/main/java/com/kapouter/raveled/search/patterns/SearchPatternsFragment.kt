package com.kapouter.raveled.search.patterns

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.model.Pattern
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import com.kapouter.raveled.pattern.PatternActivity
import com.kapouter.raveled.search.FilterEvent
import com.kapouter.raveled.search.SearchEvent
import com.kapouter.raveled.search.filter.Filter
import com.kapouter.raveled.search.filter.FilterActivity
import com.kapouter.raveled.search.filter.FilterActivity.Companion.EXTRA_FILTER
import com.kapouter.raveled.search.filter.getQuery
import kotlinx.android.synthetic.main.fragment_search_patterns.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchPatternsFragment : Fragment() {

    companion object {
        private val LOG_TAG = SearchPatternsFragment::class.java.simpleName
        private val FILTER_REQUEST = 10
    }

    lateinit var adapter: SearchPatternsAdapter
    private var query: String? = null
    private var filters: Filter? = Filter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_patterns, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(context)
        adapter = SearchPatternsAdapter(object : SearchPatternsAdapter.OnItemClickListener {
            override fun onItemClick(item: Pattern) {
                startActivity(PatternActivity.createIntent(requireContext(), item.id))
            }
        })
        recycler.adapter = adapter

        App.api.getPatterns()
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

    private fun getData() {
        loader.visibility = View.VISIBLE
        App.api.getPatterns(query, filters?.sort?.value, filters?.craft?.getQuery(), filters?.category?.getQuery(), filters?.meterage?.getQuery(), filters?.colors)
                .compose(SchedulerTransformer())
                .subscribe(
                        { response ->
                            adapter.setItems(response.patterns)
                            recycler.scrollToPosition(0)
                            loader.visibility = View.GONE
                        },
                        { e -> Log.e(LOG_TAG, e.toString()) }
                )
    }

    @Subscribe
    fun onSearchEvent(event: SearchEvent) {
        loader.visibility = View.VISIBLE
        query = event.query
        getData()
    }

    @Subscribe
    fun onFilterEvent(event: FilterEvent) {
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