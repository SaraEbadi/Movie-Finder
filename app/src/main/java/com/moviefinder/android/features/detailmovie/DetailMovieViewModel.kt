package com.moviefinder.android.features.detailmovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviefinder.android.utils.Constants.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel(private val networkRepository: NetworkRepository) : ViewModel() {
    private val mutableLiveDataDetailsModel = MutableLiveData<DetailsModel>()
    private val compositeDisposable = CompositeDisposable()


    fun characterDetailsViewModel(id: Int): LiveData<DetailsModel> {

        compositeDisposable.add(networkRepository.getDetailMovie(id,API_KEY)
            .subscribe({
                mutableLiveDataDetailsModel.value = it
            }, {
                Log.d("detail", it.message)
            }))

        return mutableLiveDataDetailsModel
    }

    fun clear() {
        compositeDisposable.clear()

    }
}
