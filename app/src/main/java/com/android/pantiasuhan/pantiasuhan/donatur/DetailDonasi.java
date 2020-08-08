package com.android.pantiasuhan.pantiasuhan.donatur;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailDonasi extends AppCompatActivity implements View.OnClickListener{
    private EditText etId;
    private EditText etPanti;
    //uang
    private EditText etUang;
    //pegawai
    private EditText etNamaSdm, etJk, etAgama, etKeahlian, etAlamat, etTelp;

    ImageView ImageViewHolder1;

    private Button buttonUpdate;
    private Button buttonValidasi;

    private String id_donasi;
    private JSONArray result;
    String status_valid;
    Bundle extra=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();

        extra = getIntent().getExtras();
        if(extra == null){
            return;
        }

        id_donasi= extra.getString("id_donasi");

        //Inisialisasi dari View
        etPanti = (EditText) findViewById(R.id.etPanti);
        etUang = (EditText)findViewById(R.id.etUang);
        etNamaSdm = (EditText)findViewById(R.id.etNamaSDM);
        etJk = (EditText)findViewById(R.id.etJk);
        etAgama = (EditText)findViewById(R.id.etAgama);
        etKeahlian = (EditText)findViewById(R.id.etKeahlian);
        etAlamat = (EditText)findViewById(R.id.etAlamat);
        etTelp = (EditText)findViewById(R.id.etTelp);

        ImageViewHolder1 = (ImageView)findViewById(R.id.imageView1);

        buttonValidasi = (Button) findViewById(R.id.btnValidasi);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);

        // Toast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        //buttonUpdate.setOnClickListener(this);
        buttonValidasi.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

        etPanti.setEnabled(false);
        etUang.setEnabled(false);
        etNamaSdm.setEnabled(false);
        etJk.setEnabled(false);
        etAgama.setEnabled(false);
        etKeahlian.setEnabled(false);
        etAlamat.setEnabled(false);
        etTelp.setEnabled(false);

        // textViewid.setText(id_tentor);

        getData(id_donasi);
    }

    private void getData(String id_donasi) {
        String url = konfigurasi.URL_GET_EMP_DONASI;
        StringRequest stringRequest = new StringRequest(url+id_donasi,

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
        String panti = "";
        String uang = "";
        String nama = "";
        String jk = "";
        String agama = "";
        String keahlian = "";
        String alamat = "";
        String telp = "";
        String g1 = "";
        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            panti = json.getString(konfigurasi.TAG_NAMA_PANTI);
            uang = json.getString(konfigurasi.TAG_UANG);
            nama = json.getString(konfigurasi.TAG_NAMA);
            jk = json.getString(konfigurasi.TAG_JKSDM);
            agama = json.getString(konfigurasi.TAG_AGAMASDM);
            keahlian = json.getString(konfigurasi.TAG_KEAHLIAN);
            alamat = json.getString(konfigurasi.TAG_ALAMAT_DONASI);
            telp = json.getString(konfigurasi.TAG_TELP_DONASI);
            g1 = json.getString("image1");
            status_valid = json.getString("status_valid");
            //Adding the name of the student to array list
            if (status_valid.equalsIgnoreCase("1")){
            }
            //Adding the name of the student to array list





        } catch (JSONException e) {
            e.printStackTrace();
        }

//        etId.setText(id_donasi);
        etPanti.setText(panti);
        etUang.setText(uang);
        etNamaSdm.setText(nama);
        etJk.setText(jk);
        etAgama.setText(agama);
        etKeahlian.setText(keahlian);
        etAlamat.setText(alamat);
        etTelp.setText(telp);

        Glide.with(DetailDonasi.this).load(Config.URL+"pengunjung/image/"+g1)
                .fitCenter() // menyesuaikan ukuran imageview
                .crossFade() // animasi
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ImageViewHolder1);

        // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }

    private void validasiEmployee(){
        class ValidasiEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDonasi.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailDonasi.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_VALIDASI_EMP_DONASI, id_donasi);
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
                        startActivity(new Intent(DetailDonasi.this, TampilSemuaDonasi.class));
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
