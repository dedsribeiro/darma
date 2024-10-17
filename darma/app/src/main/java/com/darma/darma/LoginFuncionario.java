package com.darma.darma;

import android.app.Dialog;
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


public class LoginFuncionario extends AppCompatActivity {
    public int idFunLogado = 0;
    private EditText txFunLogin;
    private EditText txFunSenha;
    private Button btnLoginFun;
    String usuario;
    String senha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fun);


        txFunLogin = (EditText) findViewById(R.id.txtLoginFun);
        txFunSenha= (EditText) findViewById(R.id.txtSenhaFun);
        btnLoginFun=(Button) findViewById(R.id.btnEntrarFun);

        btnLoginFun.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                usuario = txFunLogin.getText().toString();
                senha = txFunSenha.getText().toString();
                if(usuario.length()==0||senha.length()==0){
                    Toast.makeText(getApplicationContext(), "Preencha os campos de Login!", Toast.LENGTH_SHORT).show();

                }else{
                    if(verificaConexao()==true) login(usuario, senha);
                    else Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();

                }


            }
        }
        );
    }

    private void login(final String usuario, String senha) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LoginFuncionario.this, "Aguarde.", "Carregando...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("usuario", uname));
                nameValuePairs.add(new BasicNameValuePair("senha", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://darmacoleta.engenharia.ws/loginFuncionario.php");
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
                s = s.replaceAll("\"", "");

                if(!s.equalsIgnoreCase("failure")){
                    idFunLogado = Integer.parseInt(s);
                    Intent intent = new Intent(LoginFuncionario.this, Menu_Fun.class);
                    //intent.putExtra(USER_NAME, username);
                    finish();
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(usuario, senha);

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
