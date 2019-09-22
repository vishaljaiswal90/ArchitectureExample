package com.architectureexample.retrofit;

import com.architectureexample.response.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("v2/everything/")
    Call<ArticleResponse> getMovieArticle(
            @Query("q") String query,
            @Query("apikey") String apiKey
    );
}
