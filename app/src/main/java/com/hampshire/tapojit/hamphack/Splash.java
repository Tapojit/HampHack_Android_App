package com.hampshire.tapojit.hamphack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * Created by tapojit on 2/16/17.
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        CountDownTimer c1=new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        };
        c1.start();

    }
}
