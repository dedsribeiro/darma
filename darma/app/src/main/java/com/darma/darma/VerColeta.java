package com.darma.darma;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class VerColeta extends AppCompatActivity implements View.OnClickListener{

    private TextView txId;
    private TextView txNome;
    private TextView txTelefone;
    private TextView txEndereco;
    private TextView txData;
    private TextView txStatus;

    private Button coletar;

    private String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_coleta);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.EMP_ID);

        txId = (TextView) findViewById(R.id.idColeta);

        txNome = (TextView) findViewById(R.id.nomeColeta);
        txTelefone = (TextView) findViewById(R.id.telefoneColeta);
        txEndereco = (TextView) findViewById(R.id.enderecoColeta);
        txData = (TextView) findViewById(R.id.dataColeta);
        txStatus = (TextView) findViewById(R.id.statusColeta);

        coletar = (Button) findViewById(R.id.btnColetar);

        coletar.setOnClickListener(this);


        txId.setText(id);

        if(verificaConexao()==true)getEmployee();
        else Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();


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
    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerColeta.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_EMP,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
           // String id = c.getString(Config.TAG_ID);
            String nome = c.getString(Config.TAG_NOME);
            String telefone = c.getString(Config.TAG_TELEFONE);
            String endereco = c.getString(Config.TAG_ENDERECO);
            String dt_coleta = c.getString(Config.TAG_DATA_COLETA);
            String status = c.getString(Config.TAG_STATUS);
            //txId.setText(id);
            txNome.setText(nome);
            txTelefone.setText(telefone);
            txEndereco.setText(endereco);
            txData.setText(dt_coleta);
            txStatus.setText(status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateColeta(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        final String dt_retirada = dateFormat.format(data_atual);


        class UpdateColeta extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerColeta.this,"Buscando Coleta","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(VerColeta.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.TAG_ID,id);
                hashMap.put(Config.KEY_DT_RETIRADA,dt_retirada);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_EMP,hashMap);

                return s;
            }
        }

        UpdateColeta ue = new UpdateColeta();
        ue.execute();
    }


    @Override
    public void onClick(View v) {
        if(v == coletar){
            updateColeta();
            Intent intentLogout = new Intent(VerColeta.this, VerTodasColetas.class);
            startActivity(intentLogout);
            finish();
        }

    }

}
