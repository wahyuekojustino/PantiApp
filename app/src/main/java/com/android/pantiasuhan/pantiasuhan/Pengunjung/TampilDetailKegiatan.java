package com.android.pantiasuhan.pantiasuhan.Pengunjung;

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

/**
 * Created by Bismillah on 25/11/2017.
 */

public class TampilDetailKegiatan extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextId;
    private EditText editTextketerangan;


    private Button buttonUpdate;
    private Button buttonDelete;

    private String id_kegiatan;
    private JSONArray result;

    Bundle extra=null;

    String status_valid;

    ImageView ImageViewHolder1, ImageViewHolder2, ImageViewHolder3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();

        extra = getIntent().getExtras();
        if(extra == null){
            return;
        }

        id_kegiatan= extra.getString("id_kegiatan");

        //Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.etIdKegiatan);
        editTextketerangan = (EditText) findViewById(R.id.etKeterangan);

        buttonDelete = (Button) findViewById(R.id.btnHapus);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);

        ImageViewHolder1 = (ImageView)findViewById(R.id.imageView1);
        ImageViewHolder2 = (ImageView)findViewById(R.id.imageView2);
        ImageViewHolder3 = (ImageView)findViewById(R.id.imageView3);

       // Toast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        //buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

        editTextketerangan.setEnabled(false);

       // textViewid.setText(id_tentor);

        getData(id_kegiatan);
    }

    private void getData(String id_kegiatan) {
        String url = konfigurasi.URL_GET_EMP_KEGIATAN;
        StringRequest stringRequest = new StringRequest(url+id_kegiatan,

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
        String id = "";
        String keterangan = "";
        String g1 = "";
        String g2 = "";
        String g3 = "";
        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            id = json.getString(konfigurasi.TAG_ID_KEGIATAN);
            keterangan = json.getString(konfigurasi.TAG_KETERANGAN);
            g1 = json.getString("image1");
            g2 = json.getString("image2");
            g3 = json.getString("image3");
            //Adding the name of the student to array list
            status_valid = json.getString("status_valid");
            //Adding the name of the student to array list
            if (status_valid.equalsIgnoreCase("1")){
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        editTextId.setText(id);
        editTextketerangan.setText(keterangan);
        Glide.with(TampilDetailKegiatan.this).load(Config.URL+"kegiatan/image/"+g1)
                .fitCenter() // menyesuaikan ukuran imageview
                .crossFade() // animasi
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ImageViewHolder1);

        Glide.with(TampilDetailKegiatan.this).load(Config.URL+"kegiatan/image/"+g2)
                .fitCenter() // menyesuaikan ukuran imageview
                .crossFade() // animasi
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ImageViewHolder2);

        Glide.with(TampilDetailKegiatan.this).load(Config.URL+"kegiatan/image/"+g3)
                .fitCenter() // menyesuaikan ukuran imageview
                .crossFade() // animasi
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ImageViewHolder3);

       // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }

/*
    private void updateEmployee(){
        final String nama_kebutuhan = editTextNama_Kebutuhan.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilDetailKegiatan.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilDetailKegiatan.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID,id_kebutuhan);
                hashMap.put(konfigurasi.KEY_EMP_KEBUTUHAN, nama_kebutuhan);

                com.sigpantikotakediri.sigpanti.DataKebutuhan.RequestHandler rh = new com.sigpantikotakediri.sigpanti.DataKebutuhan.RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();

        editTextId.setText("");
        editTextNama_Kebutuhan.setText("");
    }*/

    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilDetailKegiatan.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilDetailKegiatan.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
               RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_EMP, id_kegiatan);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(TampilDetailKegiatan.this, TampilSemuaKegiatan.class));
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
      /*  if(v == buttonUpdate){
            updateEmployee();
            startActivity(new Intent(this,TampilSemuaKebutuhan.class));
            //oast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        }*/

        if(v == buttonDelete){
            confirmDeleteEmployee();
        }
    }
}
