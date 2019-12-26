package com.project.oddbid.feature.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.oddbid.BuildConfig.POSTER_BASE
import com.project.oddbid.R
import com.project.oddbid.base.mvp.MvpActivity
import com.project.oddbid.feature.model.MovieDetailResponse
import com.project.oddbid.feature.presenter.MovieDetailPresenter
import com.project.oddbid.utils.Constant.EXTRA_ID
import com.project.oddbid.utils.Constant.SIZE_W342
import com.project.oddbid.utils.Constant.SIZE_W780
import com.project.oddbid.utils.showDialogJustPositive
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.text.NumberFormat
import java.util.*


class MovieDetailActivity : MvpActivity<MovieDetailPresenter>(), MovieDetailView {

    private var movieDetailPresenter: MovieDetailPresenter? = null
    private var spotsDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieDetailPresenter = MovieDetailPresenter(this)
        spotsDialog = SpotsDialog.Builder()
                .setContext(this)
                .build()

        val height = (getScreenWidth() / 16) * 9
        val params = CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, height)
        appBar.layoutParams = params
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        val movieId = intent.getIntExtra(EXTRA_ID, 0)
        movieDetailPresenter?.movieDetail(movieId)
    }

    override fun createPresenter(): MovieDetailPresenter {
        return MovieDetailPresenter(this)
    }

    override fun showLoading() {
        spotsDialog?.show()
    }

    override fun hideLoading() {
        spotsDialog?.dismiss()
    }

    override fun getDataSuccess(movieDetailResponse: MovieDetailResponse?) {
        val backdrop = POSTER_BASE + SIZE_W780 + movieDetailResponse?.backdropPath
        val poster = POSTER_BASE + SIZE_W342 + movieDetailResponse?.posterPath
        val title = movieDetailResponse?.title
        val overview = movieDetailResponse?.overview
        val popularity = movieDetailResponse?.popularity
        val genre = movieDetailResponse?.genres
        val releaseDate = movieDetailResponse?.releaseDate
        val homePage = movieDetailResponse?.homepage
        val productionCompany = movieDetailResponse?.productionCompanies
        val runtime = movieDetailResponse?.runtime
        val revenue = movieDetailResponse?.revenue
        val voteAverage = movieDetailResponse?.voteAverage
        val voteCount = movieDetailResponse?.voteCount
        val spokenLanguages = movieDetailResponse?.spokenLanguages

        toolbars.title = title
        setSupportActionBar(toolbars)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0F

        val cPDraw = CircularProgressDrawable(this)

        cPDraw.strokeWidth = 5f
        cPDraw.centerRadius = 30f
        cPDraw.start()

        // Backdrop
        Glide.with(this)
                .setDefaultRequestOptions(RequestOptions().placeholder(cPDraw))
                .load(backdrop)
                .into(imageBackdrop)

        val width = getScreenWidth() / 2
        val height = (width / 9) * 16
        val params = ConstraintLayout.LayoutParams(width, height)
        params.startToStart = 0
        params.endToEnd = 0
        imagePoster.layoutParams = params

        // Poster
        Glide.with(this)
                .setDefaultRequestOptions(RequestOptions().placeholder(cPDraw))
                .load(poster)
                .into(imagePoster)

        // Title
        tvTitle.text = title
        // Popularity
        tvPopularity.text = getString(R.string.txt_popularity, popularity.toString())
        // Release Date
        tvReleaseDate.text = getString(R.string.txt_release_date, releaseDate)
        // Genre
        val indicesGenre = genre?.indices ?: 0..(genre?.size?.minus(1) ?: 0)
        var genreString = ""
        for (i in indicesGenre) {
            genreString += getString(R.string.txt_many, genre?.get(i)?.name)
        }
        tvGenreDetail.text = genreString
        // Link
        tvLinkDetail.text = getString(R.string.txt_link_home_page, homePage)
        tvLinkDetail.setOnClickListener {
            val uri = Uri.parse(homePage)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        // Production Companies
        val indicesCompanies = productionCompany?.indices ?: 0..(productionCompany?.size?.minus(1)
                ?: 0)
        var productionCompanyString = ""
        for (i in indicesCompanies) {
            productionCompanyString += getString(R.string.txt_many, productionCompany?.get(i)?.name)
        }
        tvProductionCompany.text = productionCompanyString
        // Runtime
        if (runtime != null) {
            if (runtime < 60) {
                tvRuntime.text = getString(R.string.txt_runtime_minutes, runtime.toString())
            } else {
                val hours = runtime / 60
                val minutes = runtime % 60
                if (minutes == 0) {
                    tvRuntime.text = getString(R.string.txt_runtime_hours, hours.toString())
                } else {
                    tvRuntime.text = getString(R.string.txt_runtime_hours_minutes, hours.toString(), minutes.toString())
                }
            }
        }
        // Revenue
        val revenueString = NumberFormat.getNumberInstance(Locale.US).format(revenue)
        tvRevenue.text = getString(R.string.txt_revenue, revenueString)
        // Vote
        tvVote.text = getString(R.string.txt_vote, voteAverage.toString(), voteCount.toString())
        // Language
        val indicesLanguage = spokenLanguages?.indices ?: 0..(spokenLanguages?.size?.minus(1) ?: 0)
        var language = ""
        for (i in indicesLanguage) {
            language += getString(R.string.txt_many, spokenLanguages?.get(i)?.name)
        }
        tvSpokenLanguageDetail.text = language
        // Overview
        tvOverview.text = overview
    }

    override fun getDataFailed(message: String?) {
        message?.let {
            showDialogJustPositive(this, "Info Movie Detail", it, "Ok", DialogInterface.OnClickListener { _, _ -> })
        }
    }
}
