package com.project.oddbid.feature.presenter

import com.project.oddbid.BuildConfig.API_KEY
import com.project.oddbid.base.ui.BasePresenter
import com.project.oddbid.connection.NetworkCallback
import com.project.oddbid.feature.model.MovieListResponse
import com.project.oddbid.feature.model.MovieListResponseResultsItem
import com.project.oddbid.feature.view.MovieListView

class MovieListPresenter(movieListView: MovieListView) : BasePresenter<MovieListView>() {
    init {
        super.attachView(movieListView)
    }

    fun movieList(page: Int?) {
        view?.showLoading()
        apiServices?.getMovieList(API_KEY, page, null, null, null)?.let {
            addSubscribe(it, object : NetworkCallback<MovieListResponse>() {
                override fun onSuccess(model: MovieListResponse) {
                    val dataList = ArrayList<MovieListResponseResultsItem>()
                    model.results?.forEach { case ->
                        dataList.add(case)
                    }
                    view?.getDataMovieSuccess(dataList)
                }

                override fun onFailure(message: String?) {
                    view?.getDataFailed(message)
                }

                override fun onFinish() {
                    view?.hideLoading()
                }
            })
        }
    }

    fun movieListFilter(page: Int?, firstDate: String?, lastDate: String?) {
        view?.showLoading()
        apiServices?.getMovieList(API_KEY, page, null, firstDate, lastDate)?.let {
            addSubscribe(it, object : NetworkCallback<MovieListResponse>() {
                override fun onSuccess(model: MovieListResponse) {
                    val dataList = ArrayList<MovieListResponseResultsItem>()
                    model.results?.forEach { case ->
                        dataList.add(case)
                    }
                    view?.getDataMovieSuccess(dataList)
                }

                override fun onFailure(message: String?) {
                    view?.getDataFailed(message)
                }

                override fun onFinish() {
                    view?.hideLoading()
                }
            })
        }
    }

    fun movieListSortBy(page: Int?, sortBy: String?) {
        view?.showLoading()
        apiServices?.getMovieList(API_KEY, page, sortBy, null, null)?.let {
            addSubscribe(it, object : NetworkCallback<MovieListResponse>() {
                override fun onSuccess(model: MovieListResponse) {
                    val dataList = ArrayList<MovieListResponseResultsItem>()
                    model.results?.forEach { case ->
                        dataList.add(case)
                    }
                    view?.getDataMovieSuccess(dataList)
                }

                override fun onFailure(message: String?) {
                    view?.getDataFailed(message)
                }

                override fun onFinish() {
                    view?.hideLoading()
                }
            })
        }
    }
}