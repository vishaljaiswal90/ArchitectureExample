package com.architectureexample.repository;

import android.util.Log;

import com.architectureexample.response.ArticleResponse;
import com.architectureexample.retrofit.ApiRequest;
import com.architectureexample.retrofit.RetrofitRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {

    private static final String TAG =ArticleRepository.class.getSimpleName();

    ApiRequest apiRequest;

    public ArticleRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }


    public LiveData<ArticleResponse> getMovieArticle (String query ,String key){
        final MutableLiveData<ArticleResponse> data =  new MutableLiveData<>();
        apiRequest.getMovieArticle(query,key)
                .enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);

                        if (response.body() != null){
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable t) {
                        data.setValue(null);
                        Log.e("error",t.toString());
                    }
                });
        return data;
    }
}
