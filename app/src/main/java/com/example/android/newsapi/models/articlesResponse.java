package com.example.android.newsapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by emmanuel on 3/9/2018.
 */

public class articlesResponse {
    @SerializedName("articles")
    @Expose
    private List<Item> articles;

    public List<Item> getArticles(){
        return articles;
    }

}
