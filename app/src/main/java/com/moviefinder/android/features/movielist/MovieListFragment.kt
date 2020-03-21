package com.moviefinder.android.features.movielist

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviefinder.android.R
import com.moviefinder.android.base.MyApplication
import com.moviefinder.android.base.ViewModelFactory
import com.moviefinder.android.features.detailmovie.DetailMovieFragment
import com.moviefinder.android.features.movielist.movielistadapter.IMovieOnItemListener
import com.moviefinder.android.features.movielist.movielistadapter.MovieListAdapter
import com.moviefinder.android.features.movielist.movielistadapter.MovieListDiffUtils
import com.moviefinder.android.models.ResultSearch
import javax.inject.Inject

class MovieListFragment : Fragment(), IMovieOnItemListener {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var fragmentHolder: Fragment? = null
    private var movieRecyclerView: RecyclerView? = null
    private var edtMovieSearch: EditText? = null
    private var progressBar: ProgressBar? = null
    private var movieListAdapter: MovieListAdapter = MovieListAdapter(MovieListDiffUtils())
    private var resultSearchList: List<ResultSearch> = mutableListOf()
    lateinit var navController: NavController
    lateinit var MovieListViewModel: MovieListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.apiComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHolder = this
        init(view)
        MovieListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)

    }

    private fun generateMovieLists() {
        val layoutManager = LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        movieRecyclerView!!.layoutManager = layoutManager
        movieListAdapter.setOnClickListener(this)
        movieRecyclerView!!.adapter = movieListAdapter
        movieRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem = layoutManager.findLastVisibleItemPosition()
                val total = layoutManager.itemCount
                if (total > 0)
                    if (total - 1 == lastItem) {
                        progressBar!!.visibility = View.VISIBLE
                        MovieListViewModel.fetchMovieSearchData(
                            edtMovieSearch!!.text.toString(),
                            true
                        )
                    }
            }
        })

    }


    private fun init(view: View) {
        movieRecyclerView = view.findViewById(R.id.movieRecyclerView)
        edtMovieSearch = view.findViewById(R.id.edtGetMovieSearch)
        progressBar = view.findViewById(R.id.progressBar)
        val imgMovieSearch = view.findViewById<ImageView>(R.id.imgSearchMovie)
        generateMovieLists()
        imgMovieSearch.setOnClickListener {
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                (getContext() as Activity).window.currentFocus!!.windowToken,
                0
            )

            MovieListViewModel.fetchMovieSearchData(
                edtMovieSearch!!.text.toString(),
                true
            )
            MovieListViewModel.getAllMovie()
                .observe(viewLifecycleOwner, Observer { resultSearches ->
                    resultSearchList = resultSearches
                    movieListAdapter.submitList(resultSearchList)
                    progressBar!!.visibility = View.GONE
                })
        }
    }

    fun initViewModel() {


    }

    override fun onClickListener(view: View, position: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", resultSearchList[position].id)
        val detailsFragment = DetailMovieFragment()
        detailsFragment.arguments = bundle
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
        navController.navigate(R.id.action_movieListFragment_to_detailMovieFragment, bundle)

    }
}
