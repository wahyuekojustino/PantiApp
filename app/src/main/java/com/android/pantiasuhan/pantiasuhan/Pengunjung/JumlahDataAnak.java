package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

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

public class JumlahDataAnak extends AppCompatActivity{

    private EditText editTextId;
    private EditText editTextjumlah_Tot, editTextjumlah_laki, editTextjumlah_perempuan;

    private String id_pengurus;
    private JSONArray result;
    String status_valid;
    String pkey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anak_jumlah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        pkey = sharedPreferences.getString(konfigurasi.TAG_ID,"Not Available");

        //Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.etIdPengurus);
        editTextjumlah_Tot = (EditText) findViewById(R.id.etjumlahTot);
        editTextjumlah_laki = (EditText) findViewById(R.id.etjumlahLaki);
        editTextjumlah_perempuan = (EditText) findViewById(R.id.etjumlahPerempuan);


       // textViewid.setText(id_tentor);
        editTextjumlah_Tot.setEnabled(false);
        editTextjumlah_laki.setEnabled(false);
        editTextjumlah_perempuan.setEnabled(false);
        getData();

    }

    private void getData() {
        String url = konfigurasi.URL_GET_JML_ANAK+pkey;
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
        String jml_Tot = "";
        String jml_Laki = "";
        String jml_Perempuan = "";

        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            jml_Tot = json.getString("jml_tot");
            jml_Laki = json.getString("jml_laki");
            jml_Perempuan = json.getString("jml_perempuan");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        editTextId.setText(id_pengurus);
        editTextjumlah_Tot.setText(jml_Tot);
        editTextjumlah_laki.setText(jml_Laki);
        editTextjumlah_perempuan.setText(jml_Perempuan);


       // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }
}
