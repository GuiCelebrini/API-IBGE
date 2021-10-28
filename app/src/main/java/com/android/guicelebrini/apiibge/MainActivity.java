package com.android.guicelebrini.apiibge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonThread, buttorStop;

    IbgeApiThread apiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonThread = findViewById(R.id.bt_start);
        buttorStop = findViewById(R.id.bt_stop);

        buttonThread.setOnClickListener(view -> {
            apiThread = new IbgeApiThread(MainActivity.this);
            apiThread.start();
        });

        buttorStop.setOnClickListener(view -> {
            if (apiThread != null){
                Log.i("Resultado", "Thread stopped");
            }
        });

    }
}