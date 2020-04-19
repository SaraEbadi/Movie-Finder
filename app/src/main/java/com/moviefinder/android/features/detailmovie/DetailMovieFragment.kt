package com.moviefinder.android.features.detailmovie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moviefinder.android.R
import com.moviefinder.android.base.BaseFragment
import com.moviefinder.android.base.MyApplication
import com.moviefinder.android.base.ViewModelFactory
import com.moviefinder.android.models.DetailMovieResponse
import com.moviefinder.android.utils.Constants.Companion.DOLLAR_UNIT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import javax.inject.Inject

class DetailMovieFragment : BaseFragment(R.layout.detail_fragment) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var detailsViewModel: DetailMovieViewModel
    private var movieID: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.apiComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModel()
        observeDetailMovie()
        setOnClickListenerViews()
    }

    private fun init() {
        val bundle = arguments
        movieID = bundle!!.getInt("movieId", 0)
        observeDetailMovie()
    }

    private fun initViewModel() {
        detailsViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(DetailMovieViewModel::class.java)
    }

    private fun observeDetailMovie() {
        detailsViewModel.characterDetailsViewModel(movieID!!).observe(viewLifecycleOwner,
            Observer { response ->
                response.fold({
                    it.data?.let { detailResponse -> setViewWithData(detailResponse) }
                }, {
                    Log.d("detail", it.message.orEmpty())
                })
            })
    }

    private fun setViewWithData(detailMovieResponse: DetailMovieResponse) {
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original" + detailMovieResponse.backDropPath)
            .centerInside().fit().into(imgMovie)
        txtTitle.text = detailMovieResponse.originalTitle
        txtRateAverage.text = detailMovieResponse.voteAverage.toString()
        txtOverView.text = detailMovieResponse.overview
        if (detailMovieResponse.genres.isNullOrEmpty())
            txtGenres.text = detailMovieResponse.genres[0].name
        val revenue = "$DOLLAR_UNIT ${detailMovieResponse.revenue}"
        val budget = "$DOLLAR_UNIT ${detailMovieResponse.budget}"
        txtRevenue.text = revenue
        txtBudget.text = budget
    }

    private fun setOnClickListenerViews() {
        txtBudget.setOnClickListener(viewOnClick())
    }

    private fun viewOnClick(): View.OnClickListener {
        return View.OnClickListener {
            when (it) {
                txtBudget ->
                    Toast.makeText(requireContext(), "budjet", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
