package com.android.guicelebrini.apiibge.thread;

import android.os.AsyncTask;
import android.util.Log;

import com.android.guicelebrini.apiibge.model.City;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IbgeAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {

        String stringUrl = strings[0];

        InputStream inputStream = null;
        InputStreamReader inputStreamReader;
        BufferedReader reader = null;
        StringBuffer stringBuffer = null;

        try {
            URL url = new URL(stringUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);

            reader = new BufferedReader(inputStreamReader);

            stringBuffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }



        } catch (MalformedURLException e) {
            Log.e("Resultado", "doInBackground: " + e.getMessage());
        } catch (IOException e) {
            Log.e("Resultado", "doInBackground: " + e.getMessage());
        }


        return stringBuffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject object = new JSONObject(s);
            /*City myCity = new City(object.getString("id"), object.getString("nome"));
            Log.i("Cidade", myCity.toString());*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Resultado", "onPostExecute: " + s);
    }
}
