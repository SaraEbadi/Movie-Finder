package com.moviefinder.android.features.movielist

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviefinder.android.models.ResultSearch
import com.moviefinder.android.repository.network.NetworkRepository
import com.moviefinder.android.utils.Constants.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(private val networkRepository: NetworkRepository) : ViewModel() {
    private var page = 1
    private var movieName = ""
    private var list = mutableListOf<ResultSearch>()
    private var isLoading = false
    private var shouldLoadMore = true
    private val compositeDisposable = CompositeDisposable()
    private val mutableLiveDataResultSearch = MutableLiveData<List<ResultSearch>>()

    fun fetchMovieSearchData(movieName: String, isLoadMore: Boolean) {

        if (isLoading) return
        if (movieName.isNotEmpty())
            this.movieName = movieName

        prepareDataToSearch(isLoadMore)
        compositeDisposable.add(
            networkRepository.getMovieSearch(movieName,API_KEY, page)
                .subscribe({
                    isLoading = false
                    if (it.resultSearchList.isEmpty())
                        shouldLoadMore = false

                    list.addAll(it.resultSearchList)
                    mutableLiveDataResultSearch.value = list

                }, {
                    isLoading = false
                    mutableLiveDataResultSearch.value = null
                    Log.d("MyTag", it.message.orEmpty())
                })
        )
    }

    private fun prepareDataToSearch(isLoadMore: Boolean) {
        isLoading = true
        if (isLoadMore && shouldLoadMore) {
            page++
        } else {
            page = 1
            list.clear()
            shouldLoadMore = true
        }
    }


    fun getAllMovie(): LiveData<List<ResultSearch>> = mutableLiveDataResultSearch


    fun clear() {
        compositeDisposable.clear()

    }

}
