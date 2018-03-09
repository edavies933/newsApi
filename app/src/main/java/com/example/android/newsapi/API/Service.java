package com.example.android.newsapi.API;

import com.example.android.newsapi.models.articlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by emmanuel on 3/9/2018.
 */

public interface Service {

    @GET("/v2/everything?q=bitcoin&apiKey=17a2a7662fd142acb47ea71cfc447122&page=1")
    Call<articlesResponse> getItems(@Query("page") int pageNumber);
}
