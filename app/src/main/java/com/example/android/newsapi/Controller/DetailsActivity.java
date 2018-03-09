package com.example.android.newsapi.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapi.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView articleTitleTV, articleDescriptionTV, articleAuthorTV;
    private ImageView articlePictureIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String articleTitle = intent.getStringExtra("article_name");
        String articleDescription = intent.getStringExtra("article_details");
        String articlePictureURL = intent.getStringExtra("article_picture");
        String articleAuthor = intent.getStringExtra("article_author");

        articleTitleTV = findViewById(R.id.title_tv_tag);
        articleDescriptionTV = findViewById(R.id.article_details_tv);
        articlePictureIV = findViewById(R.id.article_image);
        Picasso.with(this)
                .load(articlePictureURL)
                .into(articlePictureIV);
        articleTitleTV.setText(articleTitle);
        articleDescriptionTV.setText(articleDescription);

    }

}
