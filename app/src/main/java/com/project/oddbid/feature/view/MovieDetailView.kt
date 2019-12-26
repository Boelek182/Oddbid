package com.project.oddbid.feature.view

import com.project.oddbid.feature.model.MovieDetailResponse

interface MovieDetailView {
    fun showLoading()
    fun hideLoading()
    fun getDataSuccess(movieDetailResponse: MovieDetailResponse?)
    fun getDataFailed(message: String?)
}