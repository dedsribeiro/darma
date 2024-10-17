package com.darma.darma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;

/**
 * Created by Andre on 23/04/2017.
 */

public class Help extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

    }
    public void onBackPressed(){

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
