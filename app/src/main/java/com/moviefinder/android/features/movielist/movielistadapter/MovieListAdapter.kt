package com.moviefinder.android.features.movielist.movielistadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviefinder.android.R
import com.moviefinder.android.models.ResultSearch
import com.moviefinder.android.utils.Constants.Companion.BASE_URL_IMAGE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*


class MovieListAdapter(diffCallback: DiffUtil.ItemCallback<ResultSearch>) :
    ListAdapter<ResultSearch, MovieListAdapter.MovieViewHolder>(diffCallback) {

    private var movieOnClickListener: IMovieOnItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnClickListener(movieOnClickListener: IMovieOnItemListener) {
        this.movieOnClickListener = movieOnClickListener
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        fun bind(resultSearch: ResultSearch) {
            Picasso.get()
                .load("$BASE_URL_IMAGE ${resultSearch.posterPath}")
                .centerCrop()
                .fit()
                .placeholder(R.drawable.ic_album_placeholder)
                .into(itemView.imgPoster)
            itemView.txtTitleMovie.text = resultSearch.originalTitle
            itemView.txtVoteAverage.text = resultSearch.voteAverage.toString()
            itemView.txtReleaseDate.text = resultSearch.releaseDate
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (movieOnClickListener != null) {
                movieOnClickListener!!.onClickListener(v, adapterPosition)
            }
        }
    }
}