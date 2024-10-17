package com.darma.darma;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
public class LoginAdm extends AppCompatActivity {


    public static final String ADM_ID = "ID";
    private EditText txAdmLogin;
    private EditText txAdmSenha;
    private Button btnLoginAdm;
    String cpf;
    String senha;

   // Intent in = new Intent(LoginAdm.this, Menu_Adm.class);
   // startActivity(in);
    //finish();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_adm);

        txAdmLogin = (EditText) findViewById(R.id.txtLoginAdm);
        txAdmSenha = (EditText) findViewById(R.id.txtUserFun);
        btnLoginAdm = (Button) findViewById(R.id.btnEntrarAdm);


        btnLoginAdm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cpf = txAdmLogin.getText().toString();
                senha = txAdmSenha.getText().toString();

                if(cpf.length()==0||senha.length()==0){
                    Toast.makeText(getApplicationContext(), "Preencha os campos de Login!", Toast.LENGTH_SHORT).show();

                }else{
                    if(verificaConexao()==true){
                        if(cpf.length() == 11 ) login(cpf, senha);
                        else Toast.makeText(getApplicationContext(), "CPF inválido! Insira um CPF válido.", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();

                }



            }
        }
        );
    }

    private void login(final String cpf, String senha) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LoginAdm.this, "Aguarde.", "Carregando...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("cpf", uname));
                nameValuePairs.add(new BasicNameValuePair("senha", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://darmacoleta.engenharia.ws/loginAdm.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(LoginAdm.this, Menu_Adm.class);
                    //intent.putExtra(USER_NAME, username);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(cpf, senha);

    }


    public void onBackPressed(){

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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
