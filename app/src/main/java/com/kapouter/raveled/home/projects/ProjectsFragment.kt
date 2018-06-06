package com.kapouter.raveled.home.projects

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.fragment_projects.*

class ProjectsFragment : Fragment() {

    companion object {
        private val LOG_TAG = ProjectsFragment::class.java.simpleName
    }

    lateinit var adapter: ProjectsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_projects, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = GridLayoutManager(context, 2)
        adapter = ProjectsAdapter()
        recycler.adapter = adapter

        App.api.getProjects(App.user!!.username, "status completed_ status_changed_")
                .compose(SchedulerTransformer())
                .subscribe({ response ->
                    adapter.setItems(response.projects)
                }, { e -> Log.e(LOG_TAG, e.toString()) })
    }
}