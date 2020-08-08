package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

public class TampilDetailKebutuhan extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextId;
    //uang
    private EditText etUang, etDigunakan;
    //alat
    private EditText etBarang, etJumlah;
    //pegawai
    private EditText etNamaSdm, etJk, etAgama, etKeahlian, etJumlahSdm;


    private Button buttonUpdate;
    private Button buttonDelete;

    private String id_kebutuhan;
    private JSONArray result;
    String status_valid;
    Bundle extra=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kebutuhan_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();

        extra = getIntent().getExtras();
        if(extra == null){
            return;
        }

        id_kebutuhan= extra.getString("id_kebutuhan");

        //Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.etIdkebutuhan);
        etUang = (EditText)findViewById(R.id.etUang);
        etDigunakan = (EditText)findViewById(R.id.etDigunakan);
        etBarang = (EditText)findViewById(R.id.etBarang);
        etJumlah = (EditText)findViewById(R.id.etJumlah);
        etNamaSdm = (EditText)findViewById(R.id.etNamaSDM);
        etJk = (EditText)findViewById(R.id.etJk);
        etAgama = (EditText)findViewById(R.id.etAgama);
        etKeahlian = (EditText)findViewById(R.id.etKeahlian);
        etJumlahSdm = (EditText)findViewById(R.id.etJumlahSdm);

        buttonDelete = (Button) findViewById(R.id.btnHapus);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);

       // Toast.makeText(this, id_tentor, Toast.LENGTH_SHORT).show();
        //buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

        etUang.setEnabled(false);
        etDigunakan.setEnabled(false);
        etBarang.setEnabled(false);
        etJumlah.setEnabled(false);
        etNamaSdm.setEnabled(false);
        etJk.setEnabled(false);
        etAgama.setEnabled(false);
        etKeahlian.setEnabled(false);
        etJumlahSdm.setEnabled(false);

       // textViewid.setText(id_tentor);

        getData(id_kebutuhan);
    }

    private void getData(String id_kebutuhan) {
        String url = konfigurasi.URL_GET_EMP_KEBUTUHAN;
        StringRequest stringRequest = new StringRequest(url+id_kebutuhan,

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
        String uang = "";
        String keperluam = "";
        String barang = "";
        String jumlah = "";
        String nama = "";
        String jk = "";
        String agama = "";
        String keahlian = "";
        String jumlahsdm = "";
        try {
            //Getting json object
            JSONObject json = result.getJSONObject(0);

            uang = json.getString(konfigurasi.TAG_UANG);
            keperluam = json.getString(konfigurasi.TAG_KEPERLUAN);
            barang = json.getString(konfigurasi.TAG_BARANG);
            jumlah = json.getString(konfigurasi.TAG_JUMLAH);
            nama = json.getString(konfigurasi.TAG_NAMA);
            jk = json.getString(konfigurasi.TAG_JKSDM);
            agama = json.getString(konfigurasi.TAG_AGAMASDM);
            keahlian = json.getString(konfigurasi.TAG_KEAHLIAN);
            jumlahsdm = json.getString(konfigurasi.TAG_JUMLAHSDM);
            status_valid = json.getString("status_valid");
            //Adding the name of the student to array list
            if (status_valid.equalsIgnoreCase("1")){
            }
            //Adding the name of the student to array list





        } catch (JSONException e) {
            e.printStackTrace();
        }

        editTextId.setText(id_kebutuhan);
        etUang.setText(uang);
        etDigunakan.setText(keperluam);
        etBarang.setText(barang);
        etJumlah.setText(jumlah);
        etNamaSdm.setText(nama);
        etJk.setText(jk);
        etAgama.setText(agama);
        etKeahlian.setText(keahlian);
        etJumlahSdm.setText(jumlahsdm);
       // Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

    }
}
