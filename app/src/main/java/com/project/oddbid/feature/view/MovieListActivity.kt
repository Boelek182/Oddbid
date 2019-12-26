package com.project.oddbid.feature.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.oddbid.R
import com.project.oddbid.base.mvp.MvpActivity
import com.project.oddbid.external.startActivity
import com.project.oddbid.feature.adapter.MovieAdapter
import com.project.oddbid.feature.model.MovieListResponseResultsItem
import com.project.oddbid.feature.presenter.MovieListPresenter
import com.project.oddbid.utils.Constant.EXTRA_FILTER
import com.project.oddbid.utils.Constant.EXTRA_FIRST_DATE
import com.project.oddbid.utils.Constant.EXTRA_LAST_DATE
import com.project.oddbid.utils.Constant.EXTRA_SETTING
import com.project.oddbid.utils.Constant.EXTRA_SORT
import com.project.oddbid.utils.Constant.EXTRA_SORT_BY
import com.project.oddbid.utils.showDialogJustPositive
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : MvpActivity<MovieListPresenter>(), MovieListView {

    private var movieListPresenter: MovieListPresenter? = null
    private var spotsDialog: AlertDialog? = null
    private var movieList: MutableList<MovieListResponseResultsItem>? = null
    private var pages: Int? = 1
    private var sortBy: String? = null
    private var firstDate: String? = null
    private var lastDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        movieListPresenter = MovieListPresenter(this)
        spotsDialog = SpotsDialog.Builder()
                .setContext(this)
                .build()
    }

    override fun onStart() {
        super.onStart()

        movieList = ArrayList()

        if (intent.extras != null) {
            when (intent.getStringExtra(EXTRA_SETTING)) {
                EXTRA_FILTER -> {
                    firstDate = intent.getStringExtra(EXTRA_FIRST_DATE)
                    lastDate = intent.getStringExtra(EXTRA_LAST_DATE)
                    movieListPresenter?.movieListFilter(pages, firstDate, lastDate)
                }
                EXTRA_SORT -> {
                    sortBy = intent.getStringExtra(EXTRA_SORT_BY)
                    movieListPresenter?.movieListSortBy(pages, sortBy)
                }
            }
        } else {
            movieListPresenter?.movieList(pages)
        }

        activityMovieListSwp.setOnRefreshListener { movieListPresenter?.movieList(pages) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                startActivity<SettingActivity>(EXTRA_SETTING to EXTRA_FILTER)
                return true
            }
            R.id.action_sort -> {
                startActivity<SettingActivity>(EXTRA_SETTING to EXTRA_SORT)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun createPresenter(): MovieListPresenter {
        return MovieListPresenter(this)
    }

    override fun showLoading() {
        spotsDialog?.show()
    }

    override fun hideLoading() {
        activityMovieListSwp.isRefreshing = false
        spotsDialog?.dismiss()
    }

    override fun getDataMovieSuccess(movieListResponseResultsItem: ArrayList<MovieListResponseResultsItem>?) {
        if (pages == 1) {
            movieList?.clear()
            movieListResponseResultsItem?.let { movieList?.addAll(it) }
        } else {
            movieListResponseResultsItem?.let { movieList?.addAll(it) }
        }

        activityMovieListRv.setHasFixedSize(true)
        activityMovieListRv.layoutManager = LinearLayoutManager(this)
        val adapter = MovieAdapter(movieList, this)
        adapter.notifyDataSetChanged()
        activityMovieListRv.adapter = adapter
    }

    override fun getDataFailed(message: String?) {
        message?.let {
            showDialogJustPositive(this, "Info", it, "Ok", DialogInterface.OnClickListener { _, _ -> })
        }
    }
}