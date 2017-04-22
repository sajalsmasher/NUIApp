package com.example.eaishwary.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashScreen extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread mythread= new Thread(){
            @Override
            public void run()
            {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(getApplicationContext(),IntroActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }
}
