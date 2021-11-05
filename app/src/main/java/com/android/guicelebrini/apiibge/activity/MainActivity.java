package com.android.guicelebrini.apiibge.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.guicelebrini.apiibge.R;
import com.android.guicelebrini.apiibge.model.City;
import com.android.guicelebrini.apiibge.thread.IbgeAsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button buttonGetApi;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();

        buttonGetApi.setOnClickListener(view -> {
            IbgeAsyncTask task = new IbgeAsyncTask();

            String jsonResponse = "";

            String statesInBrazil = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
            String urlCitiesInRj = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/rj/municipios";
            String urlCity = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/3306156";
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


        });

    }

    private void findViewsById() {
        buttonGetApi = findViewById(R.id.buttonGetApi);
        textResult = findViewById(R.id.textResult);
    }
}