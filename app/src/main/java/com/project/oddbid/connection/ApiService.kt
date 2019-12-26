package com.project.oddbid.connection

import com.project.oddbid.feature.model.GenresItem
import com.project.oddbid.feature.model.MovieDetailResponse
import com.project.oddbid.utils.Constant.GENRE_DETAIL
import com.project.oddbid.utils.Constant.MOVIE_DETAIL
import com.project.oddbid.utils.Constant.MOVIE_LIST
import com.project.oddbid.feature.model.MovieListResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(MOVIE_LIST)
    fun getMovieList(@Query(value = "api_key", encoded = true) apiKey: String?,
                     @Query(value = "page", encoded = true) page: Int?,
                     @Query(value = "sort_by", encoded = true) sortBy: String?,
                     @Query(value = "primary_release_date.gte", encoded = true) firstDate: String?,
                     @Query(value = "primary_release_date.lte", encoded = true) lastDate: String?): Observable<MovieListResponse>?

    @GET(MOVIE_DETAIL)
    fun getMovieDetail(@Path("id") movieId: Int?,
                       @Query(value = "api_key", encoded = true) apiKey: String?): Observable<MovieDetailResponse>?

    @GET(GENRE_DETAIL)
    fun getGenreDetail(@Path("id") genreId: Int?,
                       @Query(value = "api_key", encoded = true) apiKey: String?): Call<GenresItem>?
}