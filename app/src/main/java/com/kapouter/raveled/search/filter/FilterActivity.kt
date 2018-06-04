package com.kapouter.raveled.search.filter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class FilterActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = FilterActivity::class.java.simpleName
        private val EXTRA_FILTER = "EXTRA_FILTER"

        fun createIntent(context: Context): Intent = Intent(context, FilterActivity::class.java)

        fun createIntent(context: Context, filter: Filter): Intent {
            val intent = Intent(context, FilterActivity::class.java)
            intent.putExtra(EXTRA_FILTER, filter)
            return intent
        }
    }

    private var filters = Filter()

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
    }

    private fun initFilters() {

    }

    private fun clearFilters() {

    }

    private fun filter() {
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