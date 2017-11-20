package com.kapouter.raveled.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {

    companion object {
        private val LOG_TAG = HomeFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.api.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            Log.d(LOG_TAG, user.user.username)
                            App.api.getProjects(user.user.username)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            { response ->
                                                Log.d(LOG_TAG, response.projects.size.toString())
                                            },
                                            { e ->
                                                Log.d(LOG_TAG, e.message)
                                            }
                                    )
                        },
                        { e ->
                            Log.d(LOG_TAG, e.message)
                        })
    }
}