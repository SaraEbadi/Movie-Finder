package com.moviefinder.android.features.detailmovie

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.moviefinder.android.R
import com.moviefinder.android.base.MyApplication
import com.moviefinder.android.base.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import javax.inject.Inject

class DetailMovieFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.apiComponent.inject(this)
    }
    private var fragmentHolder: Fragment? = null
    lateinit var detailsViewModel: DetailMovieViewModel
    private var movieID: Int ? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_fragment, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHolder = this
        init()
        detailsViewModel = ViewModelProviders.of(this,viewModelFactory).get(DetailMovieViewModel::class.java)

        setViewWithData()
    }

    private fun init() {
        val bundle = arguments
        movieID = bundle!!.getInt("movieId", 0)
    }

    private fun setViewWithData() {
        detailsViewModel.characterDetailsViewModel(movieID!!).observe(viewLifecycleOwner, Observer { detailsModel ->
            Picasso.get()
                .load("https://image.tmdb.org/t/p/original" + detailsModel.backDropPath)
                .centerInside()
                .fit()
                .into(imgMovie)
            txtTitle!!.text = detailsModel.originalTitle
            txtRateAverage!!.text = detailsModel.voteAverage.toString()
            txtOverView!!.text = detailsModel.overview

            if (!detailsModel.genres.isNullOrEmpty())
                txtGenres!!.text = detailsModel.genres[0].name

            txtRevenue!!.text = detailsModel.revenue.toString() + " $"
            txtBudget!!.text = detailsModel.budget.toString() + " $"
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.clear()
    }
}
