package com.darma.darma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Danilo on 07/05/2017.
 */

public class Relatorios extends AppCompatActivity {

    private Button espera;
    private Button recolhido;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_adm);

        espera = (Button) findViewById(R.id.btnEspera);
        recolhido = (Button) findViewById(R.id.btnRecolhido);

        espera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Relatorios.this, VerColetasEspera.class);
                startActivity(i);
                finish();

            }
        });

        recolhido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Relatorios.this, VerColetasFechada.class);
                startActivity(i);
                finish();

            }
        });

    }
    public void onBackPressed(){

        Intent i = new Intent(this, Menu_Adm.class);
        startActivity(i);
        finish();

    }
}
