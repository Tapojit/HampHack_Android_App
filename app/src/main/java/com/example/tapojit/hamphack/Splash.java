package com.example.tapojit.hamphack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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

//        final ImageView iv=(ImageView) findViewById(R.id.spl);
//        final Animation an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
//        final Animation an2=AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
//
//        iv.startAnimation(an);
//        an.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                iv.startAnimation(an2);
//                finish();
//                Intent i=new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }
}
