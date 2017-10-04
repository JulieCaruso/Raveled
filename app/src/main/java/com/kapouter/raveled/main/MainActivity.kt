package com.kapouter.raveled.main

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import com.kapouter.raveled.R
import com.kapouter.raveled.home.HomeFragment
import com.kapouter.raveled.notebook.NotebookFragment
import com.kapouter.raveled.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_appbar.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        bottomMenu.setOnNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                R.id.home -> {
                    switchToHome()
                    true
                }
                R.id.search -> {
                    switchToSearch()
                    true
                }
                R.id.notebook -> {
                    switchToNotebook()
                    true
                }
                else -> false
            }
        })

        bottomMenu.selectedItemId = R.id.search
    }

    fun switchToHome() {
        val params = appbar_layout.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior?.onNestedFling(coordinator_layout, appbar_layout, null, 0f, -1000f, true)

        val fragment = HomeFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    fun switchToSearch() {
        val params = appbar_layout.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior?.onNestedFling(coordinator_layout, appbar_layout, null, 0f, -1000f, true)

        val fragment = SearchFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    fun switchToNotebook() {
        val params = appbar_layout.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior?.onNestedFling(coordinator_layout, appbar_layout, null, 0f, -1000f, true)

        val fragment = NotebookFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}
