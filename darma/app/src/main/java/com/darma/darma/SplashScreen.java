package com.darma.darma;

import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
    }

    public void onBackPressed() {
        this.finish();
    }


    @Override
    public void run() {

        startActivity(new Intent(this, MainActivity.class));
        finish();



    }
}
