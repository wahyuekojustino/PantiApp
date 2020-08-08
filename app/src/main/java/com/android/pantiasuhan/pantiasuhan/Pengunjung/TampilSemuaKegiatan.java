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

public class TampilSemuaKegiatan extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;
    String pkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_list_data_kegiatan_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        pkey = sharedPreferences.getString(konfigurasi.TAG_ID,"Not Available");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

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
                String id_kegiatan = jo.getString(konfigurasi.TAG_ID_KEGIATAN);
                String tgl = jo.getString(konfigurasi.TAG_TGL);
                String status_valid  = jo.getString("status_valid");
                String status_v = "";
                if(status_valid.equalsIgnoreCase("0")){
                    status_v = "Status : Belum Valid";
                }else if(status_valid.equalsIgnoreCase("1")){
                    status_v = "Status : Sudah Valid";
                }

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID_KEGIATAN,id_kegiatan);
                employees.put(konfigurasi.TAG_TGL, tgl);
                employees.put("status_valid",status_v);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaKegiatan.this, list, R.layout.list_item_data_kegiatan_admin,
                new String[]{konfigurasi.TAG_ID_KEGIATAN,konfigurasi.TAG_TGL,"status_valid"},
                new int[]{R.id.id_ke, R.id.nama_ke,R.id.status_valid});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaKegiatan.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_KEGIATAN+pkey);
               // System.out.println(s);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       Intent intent = new Intent(this, TampilDetailKegiatan.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(konfigurasi.TAG_ID_KEGIATAN).toString();
        intent.putExtra("id_kegiatan",empId);
        startActivity(intent);
    }
}
