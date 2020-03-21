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
    private val compositeDisposable = CompositeDisposable()


    fun characterDetailsViewModel(id: Int): LiveData<DetailMovieResponse> {
        compositeDisposable.add(networkRepository.getDetailMovie(id,API_KEY)
            .subscribe({ detailMovieResponse ->
                mutableLiveDataDetailsModel.value = detailMovieResponse
            }, {
                Log.d("detail", it.message)
            }))

        return mutableLiveDataDetailsModel
    }

    fun clear() {
        compositeDisposable.clear()

    }
}
