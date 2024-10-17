package com.darma.darma;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.util.HashMap;

public class CadastroAdm  extends AppCompatActivity implements View.OnClickListener {

    private EditText nomeAdm;
    private EditText cpfAdm;
    private EditText senhaAdm;
    private Button cadastrar;
    private Button cancelarAdm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_adm);

        nomeAdm = (EditText) findViewById(R.id.txtNomeAdm);
        cpfAdm = (EditText) findViewById(R.id.txtCpfAdm);
        senhaAdm = (EditText) findViewById(R.id.txtSenhaAdm);


        cadastrar = (Button) findViewById(R.id.btnCadastrarAdm);
        cadastrar.setOnClickListener(this);

        cancelarAdm = (Button) findViewById(R.id.btnCancelarAdm);
        cancelarAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CadastroAdm.this,Menu_Adm.class);
                startActivity(i);
                finish();

            }
        });


    }

    //Adding an adm
    private void addAdm(){

        final String nome = nomeAdm.getText().toString().trim();
        final String cpf = cpfAdm.getText().toString().trim();
        final String senha = senhaAdm.getText().toString().trim();

        class AddAdm extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CadastroAdm.this,"Adicionando Administrador","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(CadastroAdm.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_ADM_NOME,nome);
                params.put(Config.KEY_ADM_CPF,cpf);
                params.put(Config.KEY_ADM_SENHA,senha);



                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;


            }
        }

        AddAdm ae = new AddAdm();
        ae.execute();
        limpar();
    }


    @Override
    public void onClick(View v) {
        if(v == cadastrar) {
            if (verificaConexao() == true){
                if(nomeAdm.length()==0 || cpfAdm.length()==0 || senhaAdm.length() ==0) Toast.makeText(getApplicationContext(), "Preencha totos os campos do cadastro!", Toast.LENGTH_SHORT).show();
                else {
                    if(cpfAdm.length()==11) addAdm();
                    else Toast.makeText(getApplicationContext(), "Insira um CPF válido", Toast.LENGTH_SHORT).show();

                }
            }
            else Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpar(){
        nomeAdm.setText("");
        cpfAdm.setText("");
        senhaAdm.setText("");
    }

    public void onBackPressed() {

        Intent intentLogout = new Intent(CadastroAdm.this, Menu_Adm.class);
        startActivity(intentLogout);
        finish();

    }
    public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;

    }




}

