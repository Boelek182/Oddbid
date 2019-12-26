package com.project.oddbid.feature.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.oddbid.R
import com.project.oddbid.external.startActivity
import com.project.oddbid.utils.Constant.EXTRA_FILTER
import com.project.oddbid.utils.Constant.EXTRA_FIRST_DATE
import com.project.oddbid.utils.Constant.EXTRA_LAST_DATE
import com.project.oddbid.utils.Constant.EXTRA_SETTING
import com.project.oddbid.utils.Constant.EXTRA_SORT
import com.project.oddbid.utils.Constant.EXTRA_SORT_BY
import com.project.oddbid.utils.Constant.POPULARITY_ASC
import com.project.oddbid.utils.Constant.POPULARITY_DESC
import com.project.oddbid.utils.Constant.RELEASE_DATE_ASC
import com.project.oddbid.utils.Constant.RELEASE_DATE_DESC
import com.project.oddbid.utils.Constant.VOTE_COUNT_ASC
import com.project.oddbid.utils.Constant.VOTE_COUNT_DESC
import com.project.oddbid.utils.gone
import com.project.oddbid.utils.showDateTimePicker
import com.project.oddbid.utils.visible
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.content_setting_filter.*
import kotlinx.android.synthetic.main.content_setting_sort.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0F
    }

    override fun onStart() {
        super.onStart()

        if (intent.extras != null) {
            when (intent.getStringExtra(EXTRA_SETTING)) {
                EXTRA_FILTER -> filter()
                EXTRA_SORT -> sortBy()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun filter() {
        supportActionBar?.title = getString(R.string.txt_filter)
        activitySettingFilter.visible()
        activitySettingSort.gone()

        filterEtFirstDate.setOnClickListener { showDateTimePicker(this, filterEtFirstDate) }
        filterEtLastDate.setOnClickListener { showDateTimePicker(this, filterEtLastDate) }

        btnFilter.setOnClickListener {
            startActivity<MovieListActivity>(
                    EXTRA_SETTING to EXTRA_FILTER,
                    EXTRA_FIRST_DATE to filterEtFirstDate.text.toString(),
                    EXTRA_LAST_DATE to filterEtLastDate.text.toString())
            finish()
        }
    }

    private fun sortBy() {
        supportActionBar?.title = getString(R.string.txt_sort)
        activitySettingFilter.gone()
        activitySettingSort.visible()

        sortPopularityAsc.setOnClickListener {
            startActivity<MovieListActivity>(EXTRA_SETTING to EXTRA_SORT, EXTRA_SORT_BY to POPULARITY_ASC)
            finish()
        }
        sortPopularityDesc.setOnClickListener {
            startActivity<MovieListActivity>(EXTRA_SETTING to EXTRA_SORT, EXTRA_SORT_BY to POPULARITY_DESC)
            finish()
        }
        sortReleaseDateAsc.setOnClickListener {
            startActivity<MovieListActivity>(EXTRA_SETTING to EXTRA_SORT, EXTRA_SORT_BY to RELEASE_DATE_ASC)
            finish()
        }
        sortReleaseDateDesc.setOnClickListener {
            startActivity<MovieListActivity>(EXTRA_SETTING to EXTRA_SORT, EXTRA_SORT_BY to RELEASE_DATE_DESC)
            finish()
        }
        sortVoteCountAsc.setOnClickListener {
            startActivity<MovieListActivity>(EXTRA_SETTING to EXTRA_SORT, EXTRA_SORT_BY to VOTE_COUNT_ASC)
            finish()
        }
        sortVoteCountDesc.setOnClickListener {
            startActivity<MovieListActivity>(EXTRA_SETTING to EXTRA_SORT, EXTRA_SORT_BY to VOTE_COUNT_DESC)
            finish()
        }
    }
}
