package com.project.oddbid.feature.view

import com.project.oddbid.feature.model.MovieListResponseResultsItem

interface MovieListView {
    fun showLoading()
    fun hideLoading()
    fun getDataMovieSuccess(movieListResponseResultsItem: ArrayList<MovieListResponseResultsItem>?)
    fun getDataFailed(message: String?)
}