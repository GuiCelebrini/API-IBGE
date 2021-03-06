package com.android.guicelebrini.apiibge.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.guicelebrini.apiibge.R;
import com.android.guicelebrini.apiibge.api.LocationService;
import com.android.guicelebrini.apiibge.model.City;
import com.android.guicelebrini.apiibge.model.State;
import com.android.guicelebrini.apiibge.thread.IbgeAsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button buttonGetApi;
    private TextView textResult;
    //private Spinner spinnerStates, spinnerCities;

    private AutoCompleteTextView spinnerStates, spinnerCities;

    private Retrofit retrofit;

    private List<State> statesList = new ArrayList<>();
    private List<City> citiesList = new ArrayList<>();

    private String urlCitiesInRj = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/rj/municipios";
    private String urlCity = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/3306156";
    private String urlApiIbge = "https://servicodados.ibge.gov.br/api/v1/localidades/";

    private String state, completeAdress;

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
            getStatesRetrofit();
        });

        spinnerStates.setOnItemClickListener((parent, view, position, l) -> {
            String selectedState = parent.getItemAtPosition(position).toString();
            getCitiesFromStateRetrofit(selectedState);
            state = selectedState;
        });

        spinnerCities.setOnItemClickListener((parent, view, position, l) -> {
            String selectedCity = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), "Minha cidade ??: " + selectedCity, Toast.LENGTH_SHORT).show();
            completeAdress = selectedCity + ", " + state;
            Log.i("Resultado", completeAdress);
        });

    }

    private void findViewsById() {
        buttonGetApi = findViewById(R.id.buttonGetApi);
        textResult = findViewById(R.id.textResult);
        spinnerStates = findViewById(R.id.spinnerStates);
        spinnerCities = findViewById(R.id.spinnerCities);
    }

    private void getStatesRetrofit(){

        LocationService locationService = retrofit.create(LocationService.class);
        Call<List<State>> statesCall = locationService.getStates();

        statesCall.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {
                statesList = response.body();

                putInSpinnerStates(statesList);
                
                for (int i = 0; i < statesList.size(); i++){

                    State state = statesList.get(i);
                    Log.i("Estados", state.toString());
                    
                }
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {

            }
        });

    }

    private void putInSpinnerStates(List<State> statesList){

        ArrayAdapter<State> adapter = new ArrayAdapter<State>(getApplicationContext(), R.layout.dropdown_item, statesList);
        adapter.setDropDownViewResource(R.layout.dropdown_item);

        spinnerStates.setAdapter(adapter);
        //spinnerStates.setOnItemSelectedListener(this);

    }

    private void getCitiesFromStateRetrofit(String stateInitials){

        LocationService locationService = retrofit.create(LocationService.class);
        Call<List<City>> citiesCall = locationService.getCitiesFromState(stateInitials);

        citiesCall.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful()){
                    citiesList = response.body();
                    //citiesList.add(0, new City("", "Escolha uma cidade"));

                    putInSpinnerCities(citiesList);

                    for (int i = 0; i < citiesList.size(); i++){

                        City city = citiesList.get(i);
                        Log.i("Cidade", city.toString());

                    }

                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

            }
        });

    }

    private void putInSpinnerCities(List<City> citiesList) {

        ArrayAdapter<City> adapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.dropdown_item, citiesList);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        spinnerCities.setAdapter(adapter);
        //spinnerCities.setOnItemSelectedListener(this);

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

   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if (parent.getId() == R.id.spinnerStates) {
            String selectedState = parent.getItemAtPosition(position).toString();
            getCitiesFromStateRetrofit(selectedState);
        }

        if (parent.getId() == R.id.spinnerCities) {
            String selectedCity = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), "Minha cidade ??: " + selectedCity, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}