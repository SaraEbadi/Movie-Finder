package com.moviefinder.android.features.movielist.movielistadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviefinder.android.R
import com.moviefinder.android.models.ResultSearch
import com.squareup.picasso.Picasso


class MovieListAdapter(diffCallback: DiffUtil.ItemCallback<ResultSearch>) :
    ListAdapter<ResultSearch, MovieListAdapter.MovieViewHolder>(diffCallback) {

    private var resultSearchList: List<ResultSearch>? = mutableListOf()
    private var movieOnClickListener: IMovieOnItemListener? = null


    override fun submitList(list: List<ResultSearch>?) {
        super.submitList(list)
        resultSearchList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.titleMovie.text = resultSearchList!![position].originalTitle
        holder.voteAverage.text = resultSearchList!![position].voteAverage.toString()
        holder.releaseDate.text = resultSearchList!![position].releaseDate

        Picasso.get()
            .load("https://image.tmdb.org/t/p/original" + resultSearchList!![position].posterPath)
            .centerInside()
            .fit()
            .placeholder(R.drawable.ic_album_placeholder)
            .into(holder.imgPosterPath)


    }

    fun setOnClickListener(movieOnClickListener: IMovieOnItemListener) {
        this.movieOnClickListener = movieOnClickListener
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        internal var imgPosterPath: ImageView = itemView.findViewById(R.id.imgPoster)
        internal var titleMovie: TextView = itemView.findViewById(R.id.txtTitleMovie)
        internal var voteAverage: TextView = itemView.findViewById(R.id.txtVoteAverage)
        internal var releaseDate: TextView = itemView.findViewById(R.id.txtReleaseDate)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (movieOnClickListener != null) {
                movieOnClickListener!!.onClickListener(v, adapterPosition)
            }
        }
    }


}