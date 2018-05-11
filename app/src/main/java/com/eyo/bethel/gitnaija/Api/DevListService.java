package com.eyo.bethel.gitnaija.Api;

import com.eyo.bethel.gitnaija.data.DevList;
import com.eyo.bethel.gitnaija.data.DeveloperDetails;
import com.eyo.bethel.gitnaija.data.NaijaDevelopers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DevListService {

    @GET("/search/users")
    Call<DevList> getDevList(@Query("q") String filter);

    @GET("/users/{username}")
    Call<NaijaDevelopers> getDevUsernameDetails(@Path("username") String username);
}
