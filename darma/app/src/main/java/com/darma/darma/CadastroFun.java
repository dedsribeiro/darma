package com.darma.darma;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Danilo on 02/05/2017.
 */

public class CadastroFun extends AppCompatActivity implements View.OnClickListener  {

    private EditText nomeFun;
    private EditText telFun;
    private EditText userFun;
    private EditText senhaFun;
    private Button cadastrarFun;
    private Button cancelarFun;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fun);

        nomeFun = (EditText) findViewById(R.id.txtNomeFun);

        telFun = (EditText) findViewById(R.id.txtTelFun);

        userFun = (EditText) findViewById(R.id.txtUsuarioFun);
        senhaFun = (EditText) findViewById(R.id.txtSenhaFun);


        cadastrarFun = (Button) findViewById(R.id.btnCadastrarFun);
        cancelarFun = (Button) findViewById(R.id.btnCancelarFun);
        cancelarFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CadastroFun.this,Menu_Adm.class);
                startActivity(i);
                finish();

            }
        });
        cadastrarFun.setOnClickListener(this);

    }

    private void addFun(){

        final String nome = nomeFun.getText().toString().trim();
        final String telefone = telFun.getText().toString().trim();
        final String usuario = userFun.getText().toString().trim();
        final String senha = senhaFun.getText().toString().trim();

        class AddFun extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CadastroFun.this,"Cadastrando Funcionario...","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(CadastroFun.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_NOME_FUN,nome);
                params.put(Config.KEY_TELEFONE_FUN,telefone);
                params.put(Config.KEY_USUARIO_FUN,usuario);
                params.put(Config.KEY_SENHA_FUN,senha);



                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_FUN, params);
                return res;


            }
        }

        AddFun ae = new AddFun();
        ae.execute();
        limpar();
    }



    public void limpar(){
        nomeFun.setText("");
        telFun.setText("");
        senhaFun.setText("");
        userFun.setText("");
    }
    public void onBackPressed() {

        Intent intentLogout = new Intent(CadastroFun.this, Menu_Adm.class);
        startActivity(intentLogout);
        finish();

    }



    @Override
    public void onClick(View v) {
        if(v == cadastrarFun) {
            if (verificaConexao() == true){
                if(nomeFun.length()==0 || telFun.length()==0 || userFun.length()==0 || senhaFun.length()==0) Toast.makeText(getApplicationContext(), "Preencha totos os campos do cadastro!", Toast.LENGTH_SHORT).show();
                else addFun();

            }
            else Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }
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


