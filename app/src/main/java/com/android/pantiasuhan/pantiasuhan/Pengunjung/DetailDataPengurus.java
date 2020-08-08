package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class DetailDataPengurus extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextId;
    private EditText editTextNama_Pengurus;
    private EditText editTextJabatan;
    private EditText editTextAlamat_Pengurus;
    private EditText editTextTlp;


    private Button buttonUpdate;
    private Button buttonValidasi;

    private String id_pengurus;
    private JSONArray result;
    String status_valid;
    Bundle extra=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengurus_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();

        extra = getIntent().getExtras();
        if(extra == null){
            return;
        }

        id_pengurus = extra.getString("id_pengurus");

        //Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.etIdPengurus);
        editTextNama_Pengurus = (EditText) findViewById(R.id.etNamaPengurus);
        editTextJabatan = (EditText) findViewById(R.id.etJabatan);
        editTextAlamat_Pengurus = (EditText) findViewById(R.id.etAlamatPengurus);
        editTextTlp = (EditText) findViewById(R.id.etTlpPeng);

        buttonValidasi = (Button) findViewById(R.id.btnValidasi);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);

       // Toast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        //buttonUpdate.setOnClickListener(this);
        buttonValidasi.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

       // textViewid.setText(id_tentor);
        editTextNama_Pengurus.setEnabled(false);
        editTextAlamat_Pengurus.setEnabled(false);
        editTextJabatan.setEnabled(false);
        editTextAlamat_Pengurus.setEnabled(false);
        editTextTlp.setEnabled(false);

        getData(id_pengurus);
    }

    private void getData(String id_pengurus) {
        String url = konfigurasi.URL_GET_EMP_PENGURUS;
        StringRequest stringRequest = new StringRequest(url+id_pengurus,

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
        String Nama_Pengurus = "";
        String Jabatan = "";
        String Alamat_Pengurus = "";
        String tlp = "";
        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            Nama_Pengurus = json.getString(konfigurasi.TAG_NAMA_PENGURUS);
            Jabatan = json.getString(konfigurasi.TAG_JABATAN);
            Alamat_Pengurus = json.getString(konfigurasi.TAG_ALAMAT_PENGURUS);
            tlp = json.getString(konfigurasi.TAG_TLP_PENGURUS);
            //Adding the name of the student to array list

            status_valid = json.getString("status_valid");
            //Adding the name of the student to array list
            if (status_valid.equalsIgnoreCase("1")){
                buttonValidasi.setVisibility(View.GONE);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        editTextId.setText(id_pengurus);
        editTextNama_Pengurus.setText(Nama_Pengurus);
        editTextJabatan.setText(Jabatan);
        editTextAlamat_Pengurus.setText(Alamat_Pengurus);
            editTextTlp.setText(tlp);

       // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }

/*
    private void updateEmployee(){
        final String nama_pengurus = editTextNama_Pengurus.getText().toString().trim();
        final String jabatan = editTextJabatan.getText().toString().trim();
        final String alamat_pengurus = editTextAlamat_Pengurus.getText().toString().trim();
        final String tlp = editTextTlp.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDataPengurus.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailDataPengurus.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID,id_pengurus);
                hashMap.put(konfigurasi.KEY_EMP_NAMA_PENGURUS, nama_pengurus);
                hashMap.put(konfigurasi.KEY_EMP_JABATAN, jabatan);
                hashMap.put(konfigurasi.KEY_EMP_ALAMAT_PENGURUS, alamat_pengurus);
                hashMap.put(konfigurasi.KEY_EMP_TLP,tlp);

               RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();

        editTextId.setText("");
        editTextNama_Pengurus.setText("");
        editTextJabatan.setText("");
        editTextAlamat_Pengurus.setText("");
        editTextTlp.setText("");
    }
*/
private void validasiEmployee(){
    class ValidasiEmployee extends AsyncTask<Void,Void,String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(DetailDataPengurus.this, "Updating...", "Tunggu...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(DetailDataPengurus.this, s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHandler rh = new RequestHandler();
            String s = rh.sendGetRequestParam(konfigurasi.URL_VALIDASI_EMP_PENGURUS, id_pengurus);
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
                       // startActivity(new Intent(DetailDataPengurus.this, DetailDataPengurus.class));
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
