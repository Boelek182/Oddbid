package com.project.oddbid.feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.oddbid.BuildConfig
import com.project.oddbid.BuildConfig.API_KEY
import com.project.oddbid.R
import com.project.oddbid.connection.ApiService
import com.project.oddbid.connection.InitRetrofit
import com.project.oddbid.external.startActivity
import com.project.oddbid.feature.model.GenresItem
import com.project.oddbid.feature.model.MovieListResponseResultsItem
import com.project.oddbid.feature.view.MovieDetailActivity
import com.project.oddbid.utils.Constant
import com.project.oddbid.utils.Constant.EXTRA_ID
import kotlinx.android.synthetic.main.item_movies.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieAdapter(private val listProduct: MutableList<MovieListResponseResultsItem>?, private val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listProduct?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cPDraw = CircularProgressDrawable(context)

        cPDraw.strokeWidth = 5f
        cPDraw.centerRadius = 30f
        cPDraw.start()

        Glide.with(holder.itemView.context)
                .setDefaultRequestOptions(RequestOptions().placeholder(cPDraw))
                .load(BuildConfig.POSTER_BASE + Constant.SIZE_W185 + listProduct?.get(position)?.posterPath)
                .into(holder.itemView.imgItemPoster)

        holder.itemView.tvItemTitle.text = listProduct?.get(position)?.title
        holder.itemView.tvItemPopularity.text = context.getString(R.string.txt_popularity, listProduct?.get(position)?.popularity.toString())
        val indices = listProduct?.get(position)?.genreIds?.indices
                ?: (0..(listProduct?.size?.minus(1) ?: 0))
        var genreString = ""
        for (i in indices) {
            val retrofit = InitRetrofit.create()
            val genreDetail = retrofit?.create(ApiService::class.java)
            val genreResponse = genreDetail?.getGenreDetail(listProduct?.get(position)?.genreIds?.get(i), API_KEY)
            genreResponse?.enqueue(object : Callback<GenresItem> {
                override fun onFailure(call: Call<GenresItem>, t: Throwable) {
                }

                override fun onResponse(call: Call<GenresItem>, response: Response<GenresItem>) {
                    genreString += context.getString(R.string.txt_many, response.body()?.name)
                    holder.itemView.tvItemGenre.text = genreString
                }
            })
        }
        holder.itemView.tvItemDescription.text = listProduct?.get(position)?.overview

        holder.itemView.setOnClickListener { context.startActivity<MovieDetailActivity>(EXTRA_ID to listProduct?.get(position)?.id) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}