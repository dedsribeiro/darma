package com.darma.darma;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PrincipalCliente extends AppCompatActivity
        implements OnClickListener {

    private LocationManager gerenciadordeLugar = null;
    private LocationListener lugarListener = null;
    public String end = "";
    String dataHora = "";
    private EditText nomeColeta;
    private EditText telefoneColeta;

    private Button botaoPegaLugar = null;
    private Button enviarRequisição;
    private EditText editarLugar = null;
    private ProgressBar pb = null;

    private static final String TAG = "Debug";
    private Boolean flag = false;

    @Override
    public void onCreate(Bundle instanciaSalva) {
        super.onCreate(instanciaSalva);
        setContentView(R.layout.activity_cliente);

        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        editarLugar = (EditText) findViewById(R.id.editarLugar);
        nomeColeta = (EditText) findViewById(R.id.txtNomeColeta);
        telefoneColeta = (EditText) findViewById(R.id.txtTelefoneColeta);

        botaoPegaLugar = (Button) findViewById(R.id.botaoPegaLugar);
        enviarRequisição = (Button) findViewById(R.id.btnEnviarRequisicao);
        botaoPegaLugar.setOnClickListener(this);

        gerenciadordeLugar = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        enviarRequisição.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if(verificaConexao()==true){
                    addColeta();
                }else{
                    Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();

                }

            }
        }
        );

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        dataHora = dateFormat.format(data_atual);

    }

    @Override
    public void onClick(View v) {
        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");

            editarLugar.setText("Aguarde..");

            pb.setVisibility(View.VISIBLE);
            lugarListener = new MeuListener();


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                return;
            }
            gerenciadordeLugar.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, lugarListener);

        } else {
            alertbox("Gps Status!", "O seu  GPS esta desabilitado");
        }

    }
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    protected void alertbox(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("O seu  GPS esta desabilitado")
                .setCancelable(false)
                .setTitle("GPS Status")
                .setPositiveButton("GPS habilita",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent meuIntent = new Intent(
                                        Settings.ACTION_LOCALE_SETTINGS);
                                startActivity(meuIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /*----------Listener para as coordenadas ------------- */
    private class MeuListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {

            editarLugar.setText("");
            pb.setVisibility(View.INVISIBLE);
            String longitude = "Longitude: " +loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.v(TAG, latitude);
            Toast.makeText(PrincipalCliente.this, longitude +  "|||"+ latitude, Toast.LENGTH_LONG).show();



        /*---------- cidade a partir das coordenadas ------------- */
            //String nomeCidade=null;
            String strAdd = "";

            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address>  enderecos;
            try {

                enderecos = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (enderecos != null) {
                    Address returnedAddress = enderecos.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress
                                .append(returnedAddress.getAddressLine(i)).append("");
                    }
                    strAdd = strReturnedAddress.toString();

                }else {

                }

            } catch (IOException e) {
                e.printStackTrace();

            }

            String s =strAdd;
            editarLugar.setText(s);
            end = s;
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    private void addColeta() {

        final String nome = nomeColeta.getText().toString().trim();
        final String telefone = telefoneColeta.getText().toString().trim();
        final String endereco = end;
        final String dt_coleta = dataHora;

        if (nome.length() == 0 || telefone.length() == 0 || endereco.length() == 0) {
            Toast.makeText(getApplicationContext(), "Preencha os campos da requisição da coleta!", Toast.LENGTH_SHORT).show();

        } else {

            class AddColeta extends AsyncTask<Void, Void, String> {

                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(PrincipalCliente.this, "Cadastrando requisição de coleta...", "Espere...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Requisição de Coleta enviada com sucesso!", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PrincipalCliente.this, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put(Config.KEY_NOME_COLETA, nome);
                    params.put(Config.KEY_TELEFONE_COLETA, telefone);
                    params.put(Config.KEY_ENDERECO_COLETA, endereco);
                    params.put(Config.KEY_DT_COLETA, dt_coleta);

                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest(Config.URL_ADD_COLETA, params);
                    return res;


                }
            }

            AddColeta ae = new AddColeta();
            ae.execute();
            limpar();
        }
    }

    public void limpar(){
        nomeColeta.setText("");
        telefoneColeta.setText("");
        editarLugar.setText("");
    }

    public void onBackPressed() {

        Intent intentLogout = new Intent(PrincipalCliente.this, MainActivity.class);
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