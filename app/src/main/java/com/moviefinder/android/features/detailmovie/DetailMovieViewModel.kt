package com.moviefinder.android.features.detailmovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviefinder.android.models.DetailMovieResponse
import com.moviefinder.android.repository.network.NetworkRepository
import com.moviefinder.android.utils.Constants.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel(private val networkRepository: NetworkRepository) : ViewModel() {
    private val mutableLiveDataDetailsModel = MutableLiveData<DetailMovieResponse>()
    private val disposable = CompositeDisposable()

    fun characterDetailsViewModel(id: Int): LiveData<DetailMovieResponse> {
        disposable.add(networkRepository.getDetailMovie(id, API_KEY)
            .subscribe({ detailMovieResponse ->
                mutableLiveDataDetailsModel.value = detailMovieResponse
            }, {
                Log.d("detail", it.message.orEmpty())
            })
        )
        return mutableLiveDataDetailsModel
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
