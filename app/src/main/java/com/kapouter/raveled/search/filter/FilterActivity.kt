package com.kapouter.raveled.search.filter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class FilterActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = FilterActivity::class.java.simpleName
        const val EXTRA_FILTER = "EXTRA_FILTER"

        fun createIntent(context: Context, filter: Filter? = null): Intent {
            val intent = Intent(context, FilterActivity::class.java)
            if (filter != null) intent.putExtra(EXTRA_FILTER, filter)
            return intent
        }
    }

    private var filters = Filter()
    private var sortAdapter: SortAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        if (intent != null && intent.extras != null && intent.hasExtra(EXTRA_FILTER))
            filters = intent.getParcelableExtra(EXTRA_FILTER)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        filter_button.setOnClickListener { filter() }

        initFilters()
        updateFilters()
    }

    private fun initFilters() {
        sortAdapter = SortAdapter(this, object : SortAdapter.OnItemSelectedListener {
            override fun onItemSelected(item: FilterSort) {
                filters.sort = item
                sortAdapter?.notifyDataSetChanged()
            }
        })
        sort_recycler.adapter = sortAdapter
        sort_recycler.layoutManager = GridLayoutManager(this, 3)
        sortAdapter?.setItems(listOf(FilterSort.BEST, FilterSort.HOT, FilterSort.NAME, FilterSort.POPULAR, FilterSort.PROJECTS, FilterSort.FAVORITES, FilterSort.QUEUES, FilterSort.PUBLICATION, FilterSort.NEW, FilterSort.RATING, FilterSort.DIFFICULTY, FilterSort.YARN))
    }

    private fun updateFilters() {
        sortAdapter?.setSelectedItem(filters.sort)

    }

    private fun clearFilters() {
        filters = Filter()
        updateFilters()
    }

    private fun filter() {
        val intent = Intent()
        intent.putExtra(EXTRA_FILTER, filters)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear -> {
                clearFilters()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}