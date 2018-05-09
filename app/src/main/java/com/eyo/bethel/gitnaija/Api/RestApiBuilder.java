package com.eyo.bethel.gitnaija.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiBuilder {
    public static final String BASE_URL = "https://api.github.com";

    private Retrofit retrofit;

    public RestApiBuilder(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public DevListService getDevListService() {
        return retrofit.create(DevListService.class);
    }
}
