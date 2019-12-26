package com.project.oddbid.feature.presenter

import com.project.oddbid.BuildConfig.API_KEY
import com.project.oddbid.connection.NetworkCallback
import com.project.oddbid.base.ui.BasePresenter
import com.project.oddbid.feature.model.MovieDetailResponse
import com.project.oddbid.feature.view.MovieDetailView

class MovieDetailPresenter(movieDetailView: MovieDetailView): BasePresenter<MovieDetailView>() {
    init {
        super.attachView(movieDetailView)
    }

    fun movieDetail(movieId: Int?){
        view?.showLoading()
        apiServices?.getMovieDetail(movieId, API_KEY)?.let {
            addSubscribe(it, object : NetworkCallback<MovieDetailResponse>(){
                override fun onSuccess(model: MovieDetailResponse) {
                    view?.getDataSuccess(model)
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