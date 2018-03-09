package com.kapouter.raveled.pattern

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

        if (intent != null && intent.hasExtra(EXTRA_ID))
            App.api.getPattern(intent.getIntExtra(EXTRA_ID, 0))
                    .compose(SchedulerTransformer())
                    .subscribe(
                            { response -> setView(response.pattern) },
                            { e -> Log.e(LOG_TAG, e.toString()) }
                    )
    }

    private fun setView(pattern: Pattern) {
        toolbar_header.text = pattern.name
        pictures_pager.adapter = PatternPicturesPagerAdapter(layoutInflater, pattern.photos)
        picture_pager_dots.setViewPager(pictures_pager)
        designer.text = resources.getString(R.string.designed_by, pattern.pattern_author.name)
    }
}