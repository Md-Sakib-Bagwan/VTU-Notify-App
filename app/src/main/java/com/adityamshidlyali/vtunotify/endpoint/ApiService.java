package com.adityamshidlyali.vtunotify.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET(".")
    Call<String> getResponse();
}
