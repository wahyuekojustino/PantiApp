package com.android.pantiasuhan.pantiasuhan.donatur;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Bismillah on 25/11/2017.
 */

public class DetailDataDonatur extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextNIK;
    private EditText editTextNama_Donatur;
    private EditText editTextPekerjaan;
    private EditText editTextAlamat_Donatur;
    private EditText editTextTlp;
    private EditText editTextPassword;

    private Button buttonUpdate;
    private Button buttonValidasi;

    private JSONArray result;
    String status_valid;
    Bundle extra=null;
    String nik;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donatur_donasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();

//        extra = getIntent().getExtras();
//        if(extra == null){
//            return;
//        }
//
//        id_donatur = extra.getString("id_donatur");

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user  = sessionManager.getUserDetails();
        nik = user.get(SessionManager.kunci_username);

        //Inisialisasi dari View
        editTextNIK = (EditText) findViewById(R.id.etNIK);
        editTextNama_Donatur = (EditText) findViewById(R.id.etDonatur);
        editTextPassword = (EditText) findViewById(R.id.ettPassword);
        editTextPekerjaan = (EditText) findViewById(R.id.etPekerjaan);
        editTextAlamat_Donatur = (EditText) findViewById(R.id.etAlamatDonatur);
        editTextTlp = (EditText) findViewById(R.id.etTlpDon);

      //  buttonValidasi = (Button) findViewById(R.id.btnValidasi);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);

       // Toast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        //buttonUpdate.setOnClickListener(this);
//        buttonValidasi.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

//        editTextNIK.setEnabled(false);
//        editTextNama_Donatur.setEnabled(false);
//        editTextPekerjaan.setEnabled(false);
//        editTextAlamat_Donatur.setEnabled(false);
//        editTextTlp.setEnabled(false);


        getData(nik);
    }

    private void getData(String nik) {
        String url = konfigurasi.URL_GET_EMP_DONATUR;
        StringRequest stringRequest = new StringRequest(url+nik,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(konfigurasi.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            HasilAmbil(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);
    }

    private void HasilAmbil(JSONArray result) {
        String NamaDonatur = "";
        String password = "";
        String Pekerjaan = "";
        String AlamatDonatur = "";
        String tlp = "";
        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            NamaDonatur = json.getString(konfigurasi.TAG_NAMA_DONATUR);
            password = json.getString(konfigurasi.TAG_PASSWORD);
            Pekerjaan = json.getString(konfigurasi.TAG_PEKERJAAN);
            AlamatDonatur = json.getString(konfigurasi.TAG_ALAMAT_DONATUR);
            tlp = json.getString(konfigurasi.TAG_TLP_DONATUR);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        editTextNIK.setText(nik);
            editTextNama_Donatur.setText(NamaDonatur);
        editTextPassword.setText(password);
            editTextPekerjaan.setText(Pekerjaan);
            editTextAlamat_Donatur.setText(AlamatDonatur);
            editTextTlp.setText(tlp);

       // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }


    private void updateEmployee(){
        final String nama_donatur = editTextNama_Donatur.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String pekerjaan = editTextPekerjaan.getText().toString().trim();
        final String alamat_donatur = editTextAlamat_Donatur.getText().toString().trim();
        final String tlp = editTextTlp.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDataDonatur.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailDataDonatur.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_NIK,nik);
                hashMap.put(konfigurasi.KEY_EMP_NAMA_DONATUR,nama_donatur);
                hashMap.put(konfigurasi.KEY_EMP_PASSWORD,password);
                hashMap.put(konfigurasi.KEY_EMP_PEKERJAAN,pekerjaan);
                hashMap.put(konfigurasi.KEY_EMP_ALAMAT_DONATUR,alamat_donatur);
                hashMap.put(konfigurasi.KEY_EMP_TLP,tlp);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP_DONATUR,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();

        editTextNIK.setText("");
        editTextNama_Donatur.setText("");
        editTextPassword.setText("");
        editTextPekerjaan.setText("");
        editTextAlamat_Donatur.setText("");
        editTextTlp.setText("");
    }


    @Override
    public void onClick(View v) {
       if(v == buttonUpdate){
            updateEmployee();
            startActivity(new Intent(this,DetailDataDonatur.class));
            //oast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        }

//        if(v == buttonValidasi){
//            confirmValidasiEmployee();
//        }
    }
}
