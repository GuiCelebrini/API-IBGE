package com.android.guicelebrini.apiibge.thread;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;

public class IbgeApiThread extends Thread {

    private Context context;

    private int i;

    public IbgeApiThread(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        super.run();

        for (i = 1; i <= 15; i++ ){

            Log.i("Resultado", "run: counter " + i);

            ((Activity)context).runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
