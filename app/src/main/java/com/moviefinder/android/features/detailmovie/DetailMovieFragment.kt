package com.moviefinder.android.features.detailmovie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.moviefinder.android.R
import com.moviefinder.android.base.BaseFragment
import com.moviefinder.android.base.MyApplication
import com.moviefinder.android.base.ViewModelFactory
import com.moviefinder.android.models.DetailMovieResponse
import com.moviefinder.android.utils.Constants.Companion.BASE_URL_IMAGE
import com.moviefinder.android.utils.Constants.Companion.DOLLAR_UNIT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import javax.inject.Inject

class DetailMovieFragment : BaseFragment(R.layout.detail_fragment) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var movieID: Int? = null
    private val detailsViewModel: DetailMovieViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.apiComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeDetailMovie()
        setOnClickListenerViews()
    }

    private fun init() {
        val bundle = arguments
        movieID = bundle!!.getInt("movieId", 0)
        observeDetailMovie()
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

    private fun setViewWithData(data: DetailMovieResponse) {
        Picasso.get().load(BASE_URL_IMAGE + data.backDropPath)
            .centerInside().fit().into(imgMovie)
        txtTitle.text = data.originalTitle
        txtRateAverage.text = data.voteAverage.toString()
        txtOverView.text = data.overview
        txtGenres.text = data.genres[0].name
        val revenue = DOLLAR_UNIT + data.revenue
        val budget = DOLLAR_UNIT + data.budget
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
