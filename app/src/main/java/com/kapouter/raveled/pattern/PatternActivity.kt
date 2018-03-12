package com.kapouter.raveled.pattern

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.kapouter.api.model.Pattern
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.App
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.activity_pattern.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PatternActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = PatternActivity::class.java.simpleName
        private val EXTRA_ID = "EXTRA_ID"

        fun createIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, PatternActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern)

        setSupportActionBar(toolbar)

        if (intent != null && intent.hasExtra(EXTRA_ID))
            App.api.getPattern(intent.getIntExtra(EXTRA_ID, 0))
                    .compose(SchedulerTransformer())
                    .subscribe(
                            { response -> setView(response.pattern) },
                            { e -> Log.e(LOG_TAG, e.toString()) }
                    )

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setView(pattern: Pattern) {
        loader.visibility = View.GONE
        content.visibility = View.VISIBLE
        toolbar_header.text = pattern.name
        pictures_pager.adapter = PatternPicturesPagerAdapter(layoutInflater, pattern.photos)
        picture_pager_dots.setViewPager(pictures_pager)
        designer.text = resources.getString(R.string.designed_by, pattern.pattern_author.name)
        needle_sizes.text = pattern.pattern_needle_sizes.joinToString(separator = "\n", transform = { item -> item.name })
        yarn_weight.text = pattern.yarn_weight.name
        yardage.text = pattern.yardage_description
        gauge.text = pattern.gauge_description
        notes.text = Html.fromHtml(pattern.notes_html)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}