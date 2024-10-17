package com.darma.darma;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Danilo on 27/04/2017.
 */

public class Menu_Fun extends AppCompatActivity {

    private Button btnsair;
    private Button btnColeta;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fun);

        btnsair = (Button) findViewById(R.id.btnsairfun);
        btnColeta = (Button) findViewById(R.id.btnmenufunpedidos);

        btnsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_Fun.this, LoginFuncionario.class);
                startActivity(i);
                finish();

            }
        });

        btnColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_Fun.this, VerTodasColetas.class);
                startActivity(i);
                finish();

            }
        });



    }

    public void onBackPressed() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Darma");
            builder.setMessage("Deseja fechar o aplicativo?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intentLogout = new Intent(Menu_Fun.this, LoginFuncionario.class);
                    startActivity(intentLogout);
                    finish();

                }

            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intentLogout = new Intent(Menu_Fun.this, Menu_Fun.class);
                    startActivity(intentLogout);
                    finish();
                }
            });

            builder.create().show();


        }



}
