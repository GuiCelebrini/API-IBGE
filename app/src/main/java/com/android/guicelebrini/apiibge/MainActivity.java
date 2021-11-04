package com.android.guicelebrini.apiibge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

            String urlCitiesInRj = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
            String urlCity = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/3306156";

            task.execute(urlCity);

        });

    }

    private void findViewsById() {
        buttonGetApi = findViewById(R.id.buttonGetApi);
        textResult = findViewById(R.id.textResult);
    }
}