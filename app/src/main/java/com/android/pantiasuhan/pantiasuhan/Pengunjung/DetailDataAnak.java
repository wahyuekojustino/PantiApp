package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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

public class DetailDataAnak extends AppCompatActivity implements View.OnClickListener{

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextId;
    private EditText editTextNama;
    private EditText editTextTtl;
    private EditText editTextJk;
    //private EditText editTextIbu;
    //private EditText editTextAyah;
    private EditText editTextalamat;
    private EditText editTextTlp;
    private EditText editTextStatus;
    private EditText editTextAgama;
    DatePickerDialog picker;
    EditText eTgllahir;


    private Button buttonUpdate;
    private Button buttonValidasi;

    private String id_anak;
    private JSONArray result;
    String status_valid;

    Bundle extra=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anak_asuh_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();

        extra = getIntent().getExtras();
        if(extra == null){
            return;
        }

        id_anak = extra.getString("id_anak");

        //Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.etIdAnak);
        editTextNama = (EditText) findViewById(R.id.etAnakAsuh);
        editTextTtl = (EditText) findViewById(R.id.etTtl);
        editTextJk = (EditText) findViewById(R.id.etJk);
        //editTextIbu = (EditText) findViewById(R.id.etIbu);
        //editTextAyah = (EditText) findViewById(R.id.etAyah);
        editTextalamat = (EditText) findViewById(R.id.etAlamat);
        editTextTlp = (EditText) findViewById(R.id.etTlp_anak);
        editTextStatus = (EditText) findViewById(R.id.etStatus);
        buttonValidasi = (Button) findViewById(R.id.btnValidasi);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);
        editTextAgama = (EditText) findViewById(R.id.etagama);
        eTgllahir=(EditText) findViewById(R.id.etgllahir);
        eTgllahir.setInputType(InputType.TYPE_NULL);
       // Toast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        //buttonUpdate.setOnClickListener(this);
        buttonValidasi.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

       // textViewid.setText(id_tentor);

        editTextNama.setEnabled(false);
        editTextTtl.setEnabled(false);
        eTgllahir.setEnabled(false);
        editTextJk.setEnabled(false);
        //editTextIbu.setEnabled(false);
        //editTextAyah.setEnabled(false);
        editTextalamat.setEnabled(false);
        editTextTlp.setEnabled(false);
        editTextStatus.setEnabled(false);
        editTextAgama.setEnabled(false);

        getData(id_anak);

    }

    private void getData(String id_anak) {
        String url = konfigurasi.URL_GET_EMP_ANAK;
        StringRequest stringRequest = new StringRequest(url+id_anak,

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
        String nama_anak = "";
        String ttl = "";
        String tgl = "";
        String jk = "";
        //String ibu = "";
        //String ayah = "";
        String alamat_anak = "";
        String status = "";
        String tlp = "";
        String agama = "";
        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            nama_anak = json.getString(konfigurasi.TAG_NAMA_ANAK);
            ttl = json.getString(konfigurasi.TAG_TTL);
            tgl = json.getString(konfigurasi.TAG_TGLL);
            jk = json.getString(konfigurasi.TAG_JK);
            //ibu = json.getString(konfigurasi.TAG_IBU);
            //ayah = json.getString(konfigurasi.TAG_AYAH);
            agama = json.getString(konfigurasi.TAG_AGAMA);
            alamat_anak = json.getString(konfigurasi.TAG_ALAMAT);
            tlp = json.getString(konfigurasi.TAG_TLP_ANAK);
            status = json.getString(konfigurasi.TAG_STATUS);
            status_valid = json.getString("status_valid");
            //Adding the name of the student to array list
            if (status_valid.equalsIgnoreCase("1")){
                buttonValidasi.setVisibility(View.GONE);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
        editTextId.setText(id_anak);
        editTextNama.setText(nama_anak);
        editTextTtl.setText(ttl);
        eTgllahir.setText(tgl);
        editTextJk.setText(jk);
        //editTextIbu.setText(ibu);
        //editTextAyah.setText(ayah);
        editTextalamat.setText(alamat_anak);
        editTextStatus.setText(status);
        editTextTlp.setText(tlp);
        editTextAgama.setText(agama);

       // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }

/*
    private void updateEmployee(){
       final String nama_anak = editTextNama.getText().toString();
       final String ttl = editTextTtl.getText().toString();
       final String jk = editTextJk.getText().toString();
       final String ibu = editTextIbu.getText().toString();
       final String ayah = editTextAyah.getText().toString();
       final String alamat_anak = editTextalamat.getText().toString();
       final String tlp = editTextTlp.getText().toString();
       final String status = editTextStatus.getText().toString();

        class UpdateEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDataAnak.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailDataAnak.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID_ANAK,id_anak);
                hashMap.put(konfigurasi.KEY_EMP_NAMA_ANAK, nama_anak);
                hashMap.put(konfigurasi.KEY_EMP_TTL, ttl);
                hashMap.put(konfigurasi.KEY_EMP_JK, jk);
                hashMap.put(konfigurasi.KEY_EMP_IBU, ibu);
                hashMap.put(konfigurasi.KEY_EMP_AYAH, ayah);
                hashMap.put(konfigurasi.KEY_EMP_ALAMAT, alamat_anak);
                hashMap.put(konfigurasi.KEY_EMP_TLP_ANAK,tlp);
                hashMap.put(konfigurasi.KEY_EMP_STATUS, status);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP_ANAK,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();

        editTextId.setText("");
        editTextNama.setText("");
        editTextTtl.setText("");
        editTextJk.setText("");
        editTextalamat.setText("");
        editTextIbu.setText("");
        editTextAyah.setText("");
        editTextStatus.setText("");
        editTextTlp.setText("");
    }
*/
    private void validasiEmployee(){
        class ValidasiEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDataAnak.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailDataAnak.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
            RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_VALIDASI_EMP_ANAK, id_anak);
                return s;
            }
        }

        ValidasiEmployee de = new ValidasiEmployee();
        de.execute();
    }

    private void confirmValidasiEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Konfirmasi data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        validasiEmployee();
                        startActivity(new Intent(DetailDataAnak.this, TampilSemuaAnak.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
       /* if(v == buttonUpdate){
            updateEmployee();
            startActivity(new Intent(this,TampilSemuaAnak.class));
            //oast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        }*/

        if(v == buttonValidasi){
            confirmValidasiEmployee();
        }
    }
}
