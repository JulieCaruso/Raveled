package com.kapouter.raveled.pattern

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.Menu
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

    private var pattern: Pattern? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern)

        setSupportActionBar(toolbar)

        if (intent != null && intent.hasExtra(EXTRA_ID))
            App.api.getPattern(intent.getIntExtra(EXTRA_ID, 0))
                    .compose(SchedulerTransformer())
                    .subscribe(
                            { response ->
                                pattern = response.pattern
                                setView(response.pattern)
                            },
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
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pattern_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val favorite = menu.findItem(R.id.favorite)
        val unfavorite = menu.findItem(R.id.unfavorite)
        val queue = menu.findItem(R.id.queue)
        val unqueue = menu.findItem(R.id.unqueue)
        val inLibrary = menu.findItem(R.id.in_library)
        if (pattern?.personal_attributes != null) {
            favorite.isVisible = !pattern!!.personal_attributes!!.favorited
            unfavorite.isVisible = pattern!!.personal_attributes!!.favorited
            queue.isVisible = !pattern!!.personal_attributes!!.queued
            unqueue.isVisible = pattern!!.personal_attributes!!.queued
            inLibrary.icon = ContextCompat.getDrawable(this,
                    if (!pattern!!.personal_attributes!!.in_library) R.drawable.ic_library_add_white_24dp
                    else R.drawable.ic_library_books_white_24dp)
        } else {
            favorite.isVisible = true
            unfavorite.isVisible = false
            queue.isVisible = true
            unqueue.isVisible = false
            inLibrary.icon = ContextCompat.getDrawable(this, R.drawable.ic_library_add_white_24dp)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorite -> {
                pattern?.personal_attributes?.favorited = true
                invalidateOptionsMenu()
                return true
            }
            R.id.unfavorite -> {
                pattern?.personal_attributes?.favorited = false
                invalidateOptionsMenu()
                return true
            }
            R.id.queue -> {
                pattern?.personal_attributes?.queued = true
                invalidateOptionsMenu()
                return true
            }
            R.id.unqueue -> {
                pattern?.personal_attributes?.queued = false
                invalidateOptionsMenu()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}