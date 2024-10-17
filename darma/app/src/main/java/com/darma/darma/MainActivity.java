package com.darma.darma;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity  {
    private Button btnLoginAdm;
    private Button btnLoginFun;
    private Button btnLoginCliente;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoginAdm = (Button) findViewById(R.id.btnAdm);
        btnLoginFun= (Button) findViewById(R.id.btnFuncionario);
        btnLoginCliente = (Button) findViewById(R.id.btnCliente);


        btnLoginAdm.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v) {
                 Intent in = new Intent(MainActivity.this, LoginAdm.class);
                 startActivity(in);
                 finish();

             }
        }
        );

         btnLoginFun.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent i = new Intent(MainActivity.this, LoginFuncionario.class);
                 startActivity(i);
                 finish();

             }
         });

        btnLoginCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this,PrincipalCliente.class);
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
                finish();
            }

        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentLogout = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentLogout);
                finish();
            }
        });

        builder.create().show();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sobre:
                Intent i = new Intent(this, Sobre.class);
                startActivity(i);
                finish();
                return true;
            case R.id.help:
                Intent ii = new Intent(this, Help.class);
                startActivity(ii);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
