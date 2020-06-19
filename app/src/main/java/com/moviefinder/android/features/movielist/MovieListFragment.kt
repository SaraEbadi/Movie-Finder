package com.moviefinder.android.features.movielist

import android.animation.Animator
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import kotlinx.android.synthetic.main.item_search_header.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.movie_list_fragment.*
import javax.inject.Inject

class MovieListFragment : BaseFragment(R.layout.movie_list_fragment), IMovieOnItemListener
,View.OnClickListener{
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var movieListAdapter = MovieListAdapter(MovieListDiffUtils())
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager
    private val viewModel: MovieListViewModel by viewModels { viewModelFactory }
    private val resultSearchList = mutableListOf<ResultSearch>()
    private var imm: InputMethodManager? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApplication.apiComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resourceViewAssign()
        observeMovieList()
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
        imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        generateMovieLists()
        setOnClickListenerForImageSearchView()
        imgLeft.setOnClickListener(this)
        imgRight.setOnClickListener(this)
        imgClose.setOnClickListener(this)
    }

    private fun setOnClickListenerForImageSearchView() {
        imgSearchBox.setOnClickListener {
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
                    prgMovieList.visibility = View.VISIBLE
                    observeMovieList()
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
        viewModel.fetchMovieSearchData(edtSearchBox.text.toString(), true)
    }

    private fun observeMovieList() {
        viewModel.movieList().observe(viewLifecycleOwner, Observer { response ->
                response.fold({
                    movieListAdapter.submitList(it.data)
                    prgMovieList.visibility = View.GONE
                }, {
                    Log.d("MyTag", it.message.orEmpty())
                })
            })
    }

    private fun resourceViewAssign(){
        imgLeft.setImageResource(R.drawable.ic_back_white)
        imgRight.setImageResource(R.drawable.ic_search)
        imgRight.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.white))
    }

    private fun openSearchBox(v: View) {
        searchCard.visibility = View.VISIBLE
        imgLeft.isClickable = false
        imgRight.isClickable = false
        edtSearchBox.isClickable = true
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            searchCard,
            (imgRight.right + imgRight.left) / 2,
            (imgRight.top + imgRight.bottom) / 2,
            0f, v.width.toFloat()
        )
        circularReveal.duration = 300
        circularReveal.start()
    }

    private fun closeSearchBox(v: View) {
        imm?.hideSoftInputFromWindow(imgClose.windowToken, 0)
        edtSearchBox.text.clear()
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            searchCard,
            (imgRight.right + imgRight.left) / 2,
            (imgRight.top + imgRight.bottom) / 2,
            v.width.toFloat(), 0f
        )
        circularConceal.duration = 200
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                searchCard.visibility = View.INVISIBLE
                imgLeft.isClickable = true
                imgRight.isClickable = true
                edtSearchBox.isClickable = false
                circularConceal.removeAllListeners()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v){
            imgClose -> closeSearchBox(requireView())
            imgRight -> openSearchBox(requireView())
        }
    }

}
