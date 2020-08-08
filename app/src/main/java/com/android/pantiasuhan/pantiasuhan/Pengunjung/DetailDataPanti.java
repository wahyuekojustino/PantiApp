package com.android.pantiasuhan.pantiasuhan.Pengunjung;

/**
 * Created by KHAN on 03/04/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class DetailDataPanti extends AppCompatActivity {

     EditText editTextId;
     EditText editTextNamaPanti;
     EditText editTextAlamatPanti;
     EditText editTextBerdiri;
     EditText editTextTlp;
     EditText editTextTlp2;
     EditText etUsername;
    EditText etPassword;
    EditText etTotal;
    EditText etJmlLaki;
    EditText etJmlPerempuan;
    EditText etRekening;
    EditText etSK;
    EditText editTextKecamatan;
    private EditText etbank;
    private EditText etakun;


    private Button buttonUpdate;
    private Button buttonDelete;

    private String id_panti;
    private JSONArray result;
    String username;

    Button addressButton, BtnLihatPdf;
    TextView addressTV;
    TextView latLongTV;
   TextView lat;
    TextView lng;

    String url_file = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_panti);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Intent intent = getIntent();
        //id_panti = intent.getStringExtra("id_panti");
        id_panti = sharedPreferences.getString(konfigurasi.TAG_ID, "Not Available");
        //Toast.makeText(getBaseContext(), id_panti, Toast.LENGTH_LONG).show();


//Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.etIdPanti);
        editTextNamaPanti = (EditText) findViewById(R.id.etNamaPanti);
        etUsername = (EditText) findViewById(R.id.etUname);
        etPassword = (EditText) findViewById(R.id.etPaswd);
        editTextKecamatan = (EditText) findViewById(R.id.etKec);
        editTextAlamatPanti = (EditText) findViewById(R.id.etAlmatPanti);
        editTextBerdiri = (EditText) findViewById(R.id.etBediri);
        etSK = (EditText) findViewById(R.id.etSK);
        etbank= (EditText) findViewById(R.id.etbank);
        etakun = (EditText) findViewById(R.id.etakun);
        etRekening = (EditText) findViewById(R.id.etRekening);
        editTextTlp = (EditText) findViewById(R.id.etTlp);
      //  editTextTlp2 = (EditText) findViewById(R.id.etTlp2);
        etTotal = (EditText) findViewById(R.id.etJmlTotal);
        etJmlLaki = (EditText) findViewById(R.id.etJmlLaki);
        etJmlPerempuan = (EditText) findViewById(R.id.etJmlPerempuan);
        lat = (TextView) findViewById(R.id.Lat);
        lng = (TextView) findViewById(R.id.Long);

        buttonDelete = (Button) findViewById(R.id.btnHapus);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);
        BtnLihatPdf = (Button) findViewById(R.id.lihatpdf);

        etUsername.setEnabled(false);
        editTextNamaPanti.setEnabled(false);
        editTextKecamatan.setEnabled(false);
        editTextAlamatPanti.setEnabled(false);
        editTextBerdiri.setEnabled(false);
        etSK.setEnabled(false);
        etbank.setEnabled(false);
        etakun.setEnabled(false);
        etRekening.setEnabled(false);
        editTextTlp.setEnabled(false);
//        editTextTlp2.setEnabled(false);
        etJmlPerempuan.setEnabled(false);
        etJmlLaki.setEnabled(false);
        etTotal.setEnabled(false);

        getData(id_panti);

       // Toast.makeText(this, id_panti, Toast.LENGTH_SHORT).show();

        //setting awal maps
        //addressTV = (TextView) findViewById(R.id.addressTV);
        latLongTV = (TextView) findViewById(R.id.latLongTV);


        addressButton = (Button) findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

//                EditText editText = (EditText) findViewById(R.id.etAlmatPanti);
//                String address = editText.getText().toString();
//
//                GeocodingLocation locationAddress = new GeocodingLocation();
//                locationAddress.getAddressFromLocation(address,
//                        getApplicationContext(), new GeocoderHandler());

                String lati = lat.getText().toString();
                String longi = lng.getText().toString();
                Intent a = new Intent(getApplicationContext(), MapsActivity.class);
                a.putExtra("lat", lati);
                a.putExtra("long", longi);

                startActivity(a);
            }
        });

        BtnLihatPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("url_file", url_file);
                startActivity(i);
            }
        });

    }


    private void getData(String id_panti) {
        String url = konfigurasi.URL_GET_EMP;
        StringRequest stringRequest = new StringRequest(url+id_panti,

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
                            JSONObject json = result.getJSONObject(0);
                            int total = 0;
                            total = Integer.parseInt(json.getString("laki")) + Integer.parseInt(json.getString("perempuan"));

                            Toast.makeText(DetailDataPanti.this, " Total : "+ String.valueOf(total)+"\nJumlah Laki-laki : "+json.getString("laki")+"\nJumlah Perempuan : "+json.getString("perempuan"), Toast.LENGTH_LONG).show();
                            // HasilAmbil(result);
                            etTotal.setText(""+total);
                            etJmlLaki.setText(json.getString("laki"));
                            etJmlPerempuan.setText(json.getString("perempuan"));
                            editTextId.setText(json.getString("id_panti"));
                            editTextNamaPanti.setText(json.getString(konfigurasi.TAG_NAMA_PANTI));
                            etUsername.setText(json.getString(konfigurasi.TAG_USERNAME));
                            etPassword.setText(json.getString(konfigurasi.TAG_PASSWORD));
                            editTextKecamatan.setText(json.getString(konfigurasi.TAG_KECAMATAN));
                            editTextAlamatPanti.setText(json.getString(konfigurasi.TAG_ALAMAT_PANTI));
                            editTextBerdiri.setText(json.getString(konfigurasi.TAG_BERDIRI));
                            etSK.setText(json.getString(konfigurasi.TAG_SK));
                            etbank.setText(json.getString(konfigurasi.TAG_BANK));
                            etakun.setText(json.getString(konfigurasi.TAG_AKUN));
                            etRekening.setText(json.getString(konfigurasi.TAG_REKENING));
                            editTextTlp.setText(json.getString(konfigurasi.TAG_TLP));
                            lat.setText(json.getString(konfigurasi.TAG_LATI));
                            lng.setText(json.getString(konfigurasi.TAG_LONGI));
                            url_file = json.getString("url_file");



                            // HasilAmbil(result);
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
        String nama_panti = "";
        String username = "";
        String password = "";
        String Kecamatan = "";
        String alamat_panti = "";
        String berdiri = "";
        String SK = "";
        String bank = "";
        String akun = "";
        String Rekening = "";
        String tlp = "";
        String lati = "";
        String longi = "";
        String jml_lk="", jml_pr="";
        int total = 0;
        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);
            nama_panti = json.getString(konfigurasi.TAG_NAMA_PANTI);
            username = json.getString(konfigurasi.TAG_USERNAME);
            password = json.getString(konfigurasi.TAG_PASSWORD);
            Kecamatan = json.getString(konfigurasi.TAG_KECAMATAN);
            alamat_panti = json.getString(konfigurasi.TAG_ALAMAT_PANTI);
            berdiri = json.getString(konfigurasi.TAG_BERDIRI);
            SK = json.getString(konfigurasi.TAG_SK);
            bank = json.getString(konfigurasi.TAG_BANK);
            akun = json.getString(konfigurasi.TAG_AKUN);
            Rekening = json.getString(konfigurasi.TAG_REKENING);
            tlp = json.getString(konfigurasi.TAG_TLP);
            lati = json.getString(konfigurasi.TAG_LATI);
            longi = json.getString(konfigurasi.TAG_LONGI);
            //Adding the name of the student to array list
            //Adding the name of the student to array list
            total = Integer.parseInt(jml_lk) + Integer.parseInt(jml_pr);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        editTextId.setText(id_panti);
        editTextNamaPanti.setText(nama_panti);
        etUsername.setText(username);
        etPassword.setText(password);
        editTextKecamatan.setText(Kecamatan);
        editTextAlamatPanti.setText(alamat_panti);
        editTextBerdiri.setText(berdiri);
        etSK.setText(SK);
        etbank.setText(bank);
        etakun.setText(akun);
        etRekening.setText(Rekening);
        editTextTlp.setText(tlp);
        lat.setText(lati);
        lng.setText(longi);
        etTotal.setText(String.valueOf(total));
        etJmlLaki.setText(lati);
        etJmlPerempuan.setText(jml_pr);

    }

    //setting kedua maps
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

            String[] loc = locationAddress.split("&");

            String lati = loc[0];
            String longi = loc[1];

            latLongTV.setText(locationAddress);

            lat.setText(lati);
            lng.setText(longi);

            Intent a = new Intent(getApplicationContext(), MapsActivity.class);
            a.putExtra("lat", lati);
            a.putExtra("long", longi);

            startActivity(a);
        }
    }



}
