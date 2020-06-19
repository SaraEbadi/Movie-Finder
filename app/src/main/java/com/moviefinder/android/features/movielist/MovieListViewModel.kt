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
    private val movieListData = MutableLiveData<DataResource<List<ResultSearch>>>()
    private val list = mutableListOf<ResultSearch>()
    private val disposable = CompositeDisposable()
    private var shouldLoadMore = true
    private var isLoading = false
    private var page = 1

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun movieList(): LiveData<DataResource<List<ResultSearch>>> {
        return movieListData
    }

    fun fetchMovieSearchData(movieName: String, isLoadMore: Boolean) {
        if (!shouldLoadMore) return

        page++
        disposable.add(
            networkRepository.getMovieSearch(movieName, API_KEY, page)
                .subscribe({
                    if (it.resultSearchList.isEmpty())
                        shouldLoadMore = false
                    list.addAll(it.resultSearchList)
                    movieListData.value = DataResource.Success(list)
                }, {
                    movieListData.value = DataResource.Error(it.message.orEmpty())
                })
        )
    }

    private fun prepareDataToSearch(isLoadMore: Boolean) {
        isLoading = true
        if (isLoadMore && shouldLoadMore) {
        } else {
            page = 1
            list.clear()
            shouldLoadMore = true
        }
    }
}
