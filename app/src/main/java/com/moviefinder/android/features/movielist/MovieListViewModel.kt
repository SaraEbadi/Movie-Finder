package com.moviefinder.android.features.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviefinder.android.models.ResultSearch
import com.moviefinder.android.repository.network.NetworkRepository
import com.moviefinder.android.utils.Constants.Companion.API_KEY
import com.moviefinder.android.utils.DataResource
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {
    private var page = 1
    private var movieName = ""
    private var isLoading = false
    private var shouldLoadMore = true
    private val disposable = CompositeDisposable()
    private val list = mutableListOf<ResultSearch>()
    private val movieListData = MutableLiveData<DataResource<List<ResultSearch>>>()

    fun fetchMovieSearchData(movieName: String, isLoadMore: Boolean) {
        if (isLoading) return
        if (movieName.isNotEmpty())
            this.movieName = movieName

        prepareDataToSearch(isLoadMore)
        disposable.add(
            networkRepository.getMovieSearch(movieName, API_KEY, page)
                .subscribe({
                    isLoading = false
                    if (it.resultSearchList.isEmpty())
                        shouldLoadMore = false
                    list.addAll(it.resultSearchList)
                    movieListData.value = DataResource.Success(list)
                }, {
                    isLoading = false
                    movieListData.value = DataResource.Error(it.message.orEmpty())
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

    fun getAllMovie(): LiveData<DataResource<List<ResultSearch>>> = movieListData

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
