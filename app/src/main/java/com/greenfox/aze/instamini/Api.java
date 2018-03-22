package com.greenfox.aze.instamini;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by aze on 22/03/18.
 */

public interface Api {
    @POST("user")
    Call<StatusResponse> registerUser(@Body UserRequest userRequest);
}
