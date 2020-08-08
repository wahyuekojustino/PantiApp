package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.android.pantiasuhan.pantiasuhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bismillah on 25/11/2017.
 */

public class TampilSemuaDonatur extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;
    String pkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_list_data_donatur_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        SharedPreferences sharedPreferences = getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        pkey = sharedPreferences.getString(konfigurasi.TAG_ID,"Not Available");

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }


    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_donatur = jo.getString(konfigurasi.TAG_ID_DONATUR);
                String nama_donatur = jo.getString(konfigurasi.TAG_NAMA_DONATUR);
                String alamat_donatur = jo.getString(konfigurasi.TAG_ALAMAT_DONATUR);
                String status_valid  = jo.getString("status_valid");
                String status_v = "";
                if(status_valid.equalsIgnoreCase("0")){
                    status_v = "Status : Belum Valid";
                }else if(status_valid.equalsIgnoreCase("1")){
                    status_v = "Status : Sudah Valid";
                }

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID_DONATUR,id_donatur);
                employees.put(konfigurasi.TAG_NAMA_DONATUR,nama_donatur);
                employees.put(konfigurasi.TAG_ALAMAT_DONATUR,alamat_donatur);
                employees.put("status_valid",status_v);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaDonatur.this, list, R.layout.list_item_data_donatur_admin,
                new String[]{konfigurasi.TAG_ID_DONATUR, konfigurasi.TAG_NAMA_DONATUR, konfigurasi.TAG_ALAMAT_DONATUR,"status_valid"},
                new int[]{R.id.id_d, R.id.nama_d, R.id.alamat_d,R.id.status_valid});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaDonatur.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_DONATUR+pkey);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       Intent intent = new Intent(this, DetailDataDonatur.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(konfigurasi.TAG_ID_DONATUR).toString();
        intent.putExtra("id_donatur",empId);
        startActivity(intent);
    }
}
