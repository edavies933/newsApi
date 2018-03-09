package com.example.android.newsapi.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emmanuel on 3/9/2018.
 */

public class Client {

        public  static  final  String Base_URL = "https://newsapi.org";
        public static Retrofit retrofit = null;

        public static Retrofit getClient(){
            if (retrofit==null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(Base_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            return retrofit;
        }
    }

