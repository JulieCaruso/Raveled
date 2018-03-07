package com.kapouter.raveled.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        private val LOG_TAG = HomeFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.api.getProjects(App.user!!.username)
                .compose(SchedulerTransformer())
                .subscribe(
                        { response ->
                            text.setText("Hello " + App.user!!.username + " ! You have " + response.projects.size + " projects :)")
                        },
                        { e ->
                            Log.d(LOG_TAG, e.message)
                        }
                )
    }
}