package com.example.android.newsapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by emmanuel on 3/9/2018.
 */

public class Item {

    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("description")
    @Expose
    private String description;

    public Item(String urlToImage, String title, String author, String description) {
        this.urlToImage = urlToImage;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public Item() {

    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
