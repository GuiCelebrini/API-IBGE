package com.android.guicelebrini.apiibge.api;

import com.android.guicelebrini.apiibge.model.City;
import com.android.guicelebrini.apiibge.model.State;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LocationService {

    @GET("municipios/{cityId}")
    Call<City> getCity(@Path("cityId") String cityId);

    @GET("estados/")
    Call<List<State>> getStates();

    @GET("estados/{stateInitials}/municipios")
    Call<List<City>> getCitiesFromState(@Path("stateInitials") String stateInitials);

}
