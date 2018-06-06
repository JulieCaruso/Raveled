package com.kapouter.raveled.home.queue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.model.response.QueueResponse
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.fragment_queue.*

class QueueFragment : Fragment() {

    companion object {
        private val LOG_TAG = QueueFragment::class.java.simpleName
    }

    lateinit var adapter: QueueAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_queue, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(context)
        adapter = QueueAdapter()
        recycler.adapter = adapter

        App.api.getQueue(App.user!!.username)
                .compose(SchedulerTransformer())
                .subscribe({ response: QueueResponse ->
                    adapter.setItems(response.queued_projects)
                }, { e -> Log.e(LOG_TAG, e.toString()) })
    }
}