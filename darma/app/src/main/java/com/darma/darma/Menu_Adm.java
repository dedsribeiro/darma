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

public class Menu_Adm extends AppCompatActivity {

    private Button btnsair;
    private Button btnAdm;
    private Button btnFun;
    private Button btnrelatorio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adm);
        btnsair = (Button) findViewById(R.id.btnsairadm);
        btnAdm = (Button) findViewById(R.id.btnmenuadm);
        btnFun = (Button) findViewById(R.id.btnmenuadmfun);
        btnrelatorio = (Button) findViewById(R.id.btnmenuadmrelatorio);

        btnsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_Adm.this, LoginAdm.class);
                startActivity(i);
                finish();

            }
        });

        btnrelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_Adm.this, Relatorios.class);
                startActivity(i);
                finish();

            }
        });


        btnsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_Adm.this, LoginAdm.class);
                startActivity(i);
                finish();

            }
        });

        btnAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_Adm.this, CadastroAdm.class);
                startActivity(in);
                finish();

            }
        });
        btnFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_Adm.this, CadastroFun.class);
                startActivity(i);
                finish();

            }
        });

    }

    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Darma");
        builder.setMessage("Deseja fechar o aplicativo?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentLogout = new Intent(Menu_Adm.this, LoginAdm.class);
                startActivity(intentLogout);
                finish();

            }

        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentLogout = new Intent(Menu_Adm.this, Menu_Adm.class);
                startActivity(intentLogout);
                finish();
            }
        });

        builder.create().show();



    }
}
