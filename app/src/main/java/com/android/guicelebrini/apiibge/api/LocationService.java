package com.android.guicelebrini.apiibge.api;

import com.android.guicelebrini.apiibge.model.City;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationService {

    @GET("municipios/3306156")
    Call<City> getCity();

}
