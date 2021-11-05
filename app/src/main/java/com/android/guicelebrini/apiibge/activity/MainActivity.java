package com.android.guicelebrini.apiibge.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.guicelebrini.apiibge.R;
import com.android.guicelebrini.apiibge.api.LocationService;
import com.android.guicelebrini.apiibge.model.City;
import com.android.guicelebrini.apiibge.thread.IbgeAsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button buttonGetApi;
    private TextView textResult;

    private Retrofit retrofit;

    private String urlCitiesInRj = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/rj/municipios";
    private String urlCity = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/3306156";
    private String urlApiIbge = "https://servicodados.ibge.gov.br/api/v1/localidades/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();

        retrofit = new Retrofit.Builder()
                .baseUrl(urlApiIbge)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonGetApi.setOnClickListener(view -> {
            getCityRetrofit();
        });

    }

    private void findViewsById() {
        buttonGetApi = findViewById(R.id.buttonGetApi);
        textResult = findViewById(R.id.textResult);
    }

    private void getCityRetrofit(){

        String varreSaiId = "3306156";

        LocationService locationService = retrofit.create(LocationService.class);
        Call<City> cityCall = locationService.getCity(varreSaiId);

        cityCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()){
                    City city = response.body();
                    textResult.setText(city.toString());
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Deu merda", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCitiesFromApi(){
        IbgeAsyncTask task = new IbgeAsyncTask();

        String jsonResponse = "";

        String statesInBrazil = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
        String urlBitcoin = "https://blockchain.info/ticker";

        try {
            jsonResponse = task.execute(urlCitiesInRj).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gsonCities = new GsonBuilder().setPrettyPrinting().create();

        City[] cities = gsonCities.fromJson(jsonResponse, City[].class);

        textResult.setText(cities[89].toString());
    }
}