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
        private val needleSizes = listOf("-", "1.75", "2.0", "2.25", "2.5", "2.75", "3.0", "3.25", "3.5", "3.75", "4.0", "4.5", "5.0", "5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "+")

        fun createIntent(context: Context, filter: Filter? = null): Intent {
            val intent = Intent(context, FilterActivity::class.java)
            if (filter != null) intent.putExtra(EXTRA_FILTER, filter)
            return intent
        }
    }

    private var filters = Filter()
    private var sortAdapter: VignetteAdapter? = null
    private var craftAdapter: VignetteAdapter? = null
    private var categoryAdapter: VignetteAdapter? = null

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
        // SORT
        sortAdapter = VignetteAdapter(this, false, object : VignetteAdapter.OnItemSelectedListener {
            override fun onItemSelected(item: FilterItem, selectedItems: List<FilterItem>) {
                filters.sort = item
                sortAdapter?.notifyDataSetChanged()
            }
        })
        sort_recycler.adapter = sortAdapter
        sort_recycler.layoutManager = GridLayoutManager(this, 3)
        sortAdapter?.setItems(listOf(FilterItem.BEST, FilterItem.HOT, FilterItem.NAME, FilterItem.POPULAR, FilterItem.PROJECTS, FilterItem.FAVORITES, FilterItem.QUEUES, FilterItem.PUBLICATION, FilterItem.NEW, FilterItem.RATING, FilterItem.DIFFICULTY, FilterItem.YARN))

        // CRAFT
        craftAdapter = VignetteAdapter(this, true, object : VignetteAdapter.OnItemSelectedListener {
            override fun onItemSelected(item: FilterItem, selectedItems: List<FilterItem>) {
                filters.craft = selectedItems
                craftAdapter?.notifyDataSetChanged()
            }
        })
        craft_recycler.adapter = craftAdapter
        craft_recycler.layoutManager = GridLayoutManager(this, 2)
        craftAdapter?.setItems(listOf(FilterItem.CROCHET, FilterItem.KNITTING))

        // CATEGORY
        categoryAdapter = VignetteAdapter(this, true, object : VignetteAdapter.OnItemSelectedListener {
            override fun onItemSelected(item: FilterItem, selectedItems: List<FilterItem>) {
                filters.category = selectedItems
                categoryAdapter?.notifyDataSetChanged()
            }
        })
        category_recycler.adapter = categoryAdapter
        category_recycler.layoutManager = GridLayoutManager(this, 4)
        categoryAdapter?.setItems(listOf(FilterItem.PULLOVER, FilterItem.CARDIGAN, FilterItem.TOP, FilterItem.HAT, FilterItem.HAND, FilterItem.COWL, FilterItem.SCARF, FilterItem.SHAWL, FilterItem.SOCKS, FilterItem.TOYS))

        // NEEDLE SIZE
        needle_size_range.setFormatter { if (it.toIntOrNull() != null) needleSizes[it.toInt()] else it }
        needle_size_range.setOnRangeBarChangeListener { _, _, _, leftPinValue, rightPinValue ->
            var start: Float? = null
            var end: Float? = null
            if (leftPinValue != "0") start = needleSizes[leftPinValue.toInt()].toFloatOrNull()
            if (rightPinValue != "19") end = needleSizes[rightPinValue.toInt()].toFloatOrNull()
            filters.needleSize = FilterRange(start, end)
        }

        // COLORS
        colors_slider.setFormatter { if (it == "0") "" else if (it == "6") it.plus('+') else it }
        colors_slider.setOnRangeBarChangeListener { rangeBar, _, _, _, rightPinValue ->
            if (rightPinValue == "0") {
                // TODO Find a way to fix this
                rangeBar.setTemporaryPins(true)
                filters.colors = null
            } else {
                rangeBar.setTemporaryPins(false)
                filters.colors = rightPinValue.toIntOrNull()
            }
        }

        // METERAGE
        meterage_range.setFormatter {
            if (it == "2100") it.plus('+')
            else it
        }
        meterage_range.setOnRangeBarChangeListener { _, _, _, leftPinValue, rightPinValue ->
            filters.meterage.start = leftPinValue.toFloatOrNull() ?: 0f
            filters.meterage.end = if (rightPinValue == "2100") null else rightPinValue.toFloatOrNull()
        }
    }

    private fun updateFilters() {
        sortAdapter?.setSelectedItems(listOf(filters.sort))
        craftAdapter?.setSelectedItems(filters.craft)
        categoryAdapter?.setSelectedItems(filters.category)
        colors_slider.setSeekPinByValue(filters.colors?.toFloat() ?: 0f)
        meterage_range.setRangePinsByValue(filters.meterage.start ?: 0f, filters.meterage.end
                ?: 2100f)
        val needleSizeStart: Int = if (filters.needleSize?.start != null) needleSizes.indexOf(filters.needleSize!!.start!!.toString()) else 0
        val needleSizeEnd: Int = if (filters.needleSize?.end != null) needleSizes.indexOf(filters.needleSize!!.end!!.toString()) else 19
        needle_size_range.setRangePinsByValue(needleSizeStart.toFloat(), needleSizeEnd.toFloat())
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