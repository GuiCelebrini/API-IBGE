package com.android.guicelebrini.apiibge;

import android.util.Log;

public class IbgeApiThread extends Thread {

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < 15; i++ ){

            Log.i("Resultado", "run: counter " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
