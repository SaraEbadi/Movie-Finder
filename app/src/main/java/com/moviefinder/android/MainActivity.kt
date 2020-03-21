package com.moviefinder.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moviefinder.android.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //        getSupportFragmentManager().beginTransaction().replace(R.id.container,MainFragment.newInstance()).commitNow();


        //        NavHostFragment.findNavController(MainFragment.newInstance());

        //        navController = Navigation.findNavController(view);

    }


    //    public void fetchData() {
    //        Call<MovieModel> request = generateRetrofit.apiClient().getSearchMovie(Constant.API_KEY, edtMovieSearch.getText().toString());
    //        request.enqueue(new Callback<MovieModel>() {
    //            @Override
    //            public void onResponse(Call<MovieModel> call, final Response<MovieModel> response) {
    //                if (response.isSuccessful()) {
    //                    runOnUiThread(new Runnable() {
    //                        @Override
    //                        public void run() {
    //                            resultSearchList = response.body().getResultSearchList();
    //                            movieListAdapter.submitList(resultSearchList);
    //                        }
    //                    });
    //                }
    //            }
    //
    //            @Override
    //            public void onFailure(Call<MovieModel> call, Throwable t) {
    //                Log.i("onFailure", "run: " + t.getMessage());
    //            }
    //        });
    //    }
    //
    //
    //



}
