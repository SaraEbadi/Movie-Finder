package com.moviefinder.android.features.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviefinder.android.models.DetailMovieResponse
import com.moviefinder.android.repository.network.NetworkRepository
import com.moviefinder.android.utils.Constants.Companion.API_KEY
import com.moviefinder.android.utils.DataResource
import io.reactivex.disposables.CompositeDisposable

/**
 * A viewModel of detail Movie Item.
 *
 * @property networkRepository The repository is class [NetworkRepository] that make API calling DetailMovie.
 */
class DetailMovieViewModel constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {
    private val mutableLiveDataDetailsModel = MutableLiveData<DataResource<DetailMovieResponse>>()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    /**
     * Get data from server and fill mutableLiveData that mutable by changing data from the server.
     *
     * @param id The id of movie item which is clicked on it by user navigation.
     * @return A mutable liveData that is mutable for changing data from the server.
     */
    fun characterDetailsViewModel(id: Int): LiveData<DataResource<DetailMovieResponse>> {
        disposable.add(
            networkRepository.getDetailMovie(id, API_KEY)
                .subscribe({
                    mutableLiveDataDetailsModel.value = DataResource.Success(it)
                }, {
                    mutableLiveDataDetailsModel.value = DataResource.Error(it.message.orEmpty())
                })
        )
        return mutableLiveDataDetailsModel
    }
}
