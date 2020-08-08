package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * Created by Bismillah on 25/11/2017.
 */

public class JumlahDataPengurus extends AppCompatActivity{

    private EditText editTextId;
    private EditText editTextjumlah_Pengurus;

    private String id_pengurus;
    private JSONArray result;
    String status_valid;
    String pkey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengurus_jumlah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        pkey = sharedPreferences.getString(konfigurasi.TAG_ID,"Not Available");

        //Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.etIdPengurus);
        editTextjumlah_Pengurus = (EditText) findViewById(R.id.etjumlahPengurus);


       // textViewid.setText(id_tentor);
        editTextjumlah_Pengurus.setEnabled(false);
        getData();

    }

    private void getData() {
        String url = konfigurasi.URL_GET_JML_PENGURUS+pkey;
        StringRequest stringRequest = new StringRequest(url,

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
        String jml_Pengurus = "";

        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            jml_Pengurus = json.getString("jml");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        editTextId.setText(id_pengurus);
        editTextjumlah_Pengurus.setText(jml_Pengurus);


       // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }
}
