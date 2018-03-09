package com.example.android.newsapi.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.newsapi.API.Client;
import com.example.android.newsapi.API.Service;
import com.example.android.newsapi.PaginationAdapter;
import com.example.android.newsapi.PaginationScrollListener;
import com.example.android.newsapi.R;
import com.example.android.newsapi.models.Item;
import com.example.android.newsapi.models.articlesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    PaginationAdapter adapter;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private List<Item> results;
    private Service apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        adapter = new PaginationAdapter(this);



        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadNextPage();
            }


            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //init service and load data
        apiService = Client.getClient().create(Service.class);

        loadFirstPage();

    }


    private void loadFirstPage() {

        callNewsApi(currentPage).enqueue(new Callback<articlesResponse>() {
            @Override
            public void onResponse(Call<articlesResponse> call, Response<articlesResponse> response) {
                // Got data. Send it to adapter

                results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                Log.i("api response","results body contains "+ results);

                adapter.addAll(results);

                if (adapter.getItemCount() < 1000)
                    adapter.addLoadingFooter();

                else {

                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<articlesResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    private void loadNextPage() {


        callNewsApi(currentPage).enqueue(new Callback<articlesResponse>() {
            @Override
            public void onResponse(Call<articlesResponse> call, Response<articlesResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                results = fetchResults(response);
                adapter.addAll(results);

                if (adapter.getItemCount() < 1050){
                    Log.i("api response","item count "+ adapter.getItemCount());

                    adapter.addLoadingFooter(); }
                else {
                    Log.i("api response","item count is "+ adapter.getItemCount());
                    isLastPage = true;

                }
            }

            @Override
            public void onFailure(Call<articlesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Performs a Retrofit call to news API.
     */
    private Call<articlesResponse> callNewsApi(int currentPage) {
        return apiService.getItems(currentPage);
    }

    private List<Item> fetchResults(Response<articlesResponse> response) {
        articlesResponse articlesResponse = response.body();

        if (articlesResponse != null) {

            return articlesResponse.getArticles();

        }



        return results;
    }


}
