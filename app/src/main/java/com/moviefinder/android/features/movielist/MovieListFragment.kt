package com.moviefinder.android.features.movielist

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviefinder.android.R
import com.moviefinder.android.base.BaseFragment
import com.moviefinder.android.base.MyApplication
import com.moviefinder.android.base.ViewModelFactory
import com.moviefinder.android.features.detailmovie.DetailMovieFragment
import com.moviefinder.android.features.movielist.movielistadapter.IMovieOnItemListener
import com.moviefinder.android.features.movielist.movielistadapter.MovieListAdapter
import com.moviefinder.android.features.movielist.movielistadapter.MovieListDiffUtils
import com.moviefinder.android.models.ResultSearch
import kotlinx.android.synthetic.main.movie_list_fragment.*
import javax.inject.Inject

class MovieListFragment : BaseFragment(R.layout.movie_list_fragment), IMovieOnItemListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager
    private var movieListAdapter = MovieListAdapter(MovieListDiffUtils())
    private val resultSearchList = mutableListOf<ResultSearch>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.apiComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        init()
    }

    override fun onClickListener(view: View, position: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", resultSearchList[position].id)
        val detailsFragment = DetailMovieFragment()
        detailsFragment.arguments = bundle
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
        navController.navigate(R.id.action_movieListFragment_to_detailMovieFragment, bundle)

    }

    private fun init() {
        generateMovieLists()
        setOnClickListenerForImageSearchView()
    }

    private fun initViewModel() {
        movieListViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(MovieListViewModel::class.java)
    }

    private fun setOnClickListenerForImageSearchView() {
        imgSearchMovie.setOnClickListener {
            hideKeyboard()
            observeMovieList()
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            (requireActivity()).window.currentFocus!!.windowToken,
            0
        )
    }

    private fun generateMovieLists() {
        layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        movieRecyclerView.layoutManager = layoutManager
        movieListAdapter.setOnClickListener(this)
        movieRecyclerView.adapter = movieListAdapter
        scrollPaginationListener()
    }

    private fun scrollPaginationListener() {
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isFreshData()) {
                    refreshDataForPagination()
                }
            }
        })
    }

    private fun isFreshData(): Boolean {
        val lastItem = layoutManager.findLastVisibleItemPosition()
        val total = layoutManager.itemCount
        return total > 0 && total - 1 == lastItem
    }

    private fun refreshDataForPagination() {
        prgMovieList.visibility = View.VISIBLE
        movieListViewModel.fetchMovieSearchData(
            edtMovieSearch.text.toString(),
            true
        )
    }

    private fun observeMovieList() {
        movieListViewModel.getAllMovie()
            .observe(viewLifecycleOwner, Observer { response ->
                response.fold({
                    movieListAdapter.submitList(it.data)
                    prgMovieList.visibility = View.GONE
                }, {
                    Log.d("MyTag", it.message.orEmpty())
                })
            })
        movieListViewModel.fetchMovieSearchData(edtMovieSearch.text.toString(), true)
    }
}
