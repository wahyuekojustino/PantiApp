package com.android.pantiasuhan.pantiasuhan.donatur;

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

public class TampilSemuaDonasi extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;
    SessionManager sessionManager;
    String nik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_list_data_donasi_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        sessionManager = new SessionManager(getApplicationContext());

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
                String id_donasi = jo.getString(konfigurasi.TAG_ID_DONASI);
                String uang = jo.getString(konfigurasi.TAG_UANG_DONASI);
                String nama = jo.getString(konfigurasi.TAG_NAMA_DONASI);

                String status_valid  = jo.getString("status_valid");
                String status_v = "";
                if(status_valid.equalsIgnoreCase("0")){
                    status_v = "Status : Belum Valid";
                }else if(status_valid.equalsIgnoreCase("1")){
                    status_v = "Status : Sudah Valid";
                }

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID_DONASI,id_donasi);
                employees.put(konfigurasi.TAG_UANG_DONASI,uang);
                employees.put(konfigurasi.TAG_NAMA_DONASI,nama);
                employees.put("status_valid",status_v);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaDonasi.this, list, R.layout.list_item_data_donasi_admin,
                new String[]{konfigurasi.TAG_ID_DONASI,konfigurasi.TAG_UANG_DONASI,konfigurasi.TAG_NAMA_DONASI,"status_valid"},
                new int[]{R.id.id_donasi, R.id.uangdonasi, R.id.sdmdonasi, R.id.status_valid});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaDonasi.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                HashMap<String, String> user  = sessionManager.getUserDetails();
                String nik = user.get(SessionManager.kunci_username);
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_DONASI+nik);
               // System.out.println(s);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       Intent intent = new Intent(this, DetailDonasi.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(konfigurasi.TAG_ID_DONASI).toString();
        intent.putExtra("id_donasi",empId);
        startActivity(intent);
    }
}
