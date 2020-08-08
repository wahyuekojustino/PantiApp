package com.android.pantiasuhan.pantiasuhan.Pengunjung;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bismillah on 25/11/2017.
 */

public class TampilSemuaPantiJumlah extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;

    private ImageView bthapus;

    ArrayList<String> SubjectId;
    ArrayList<String> SubjectStatus;
    private String id_panti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_list_data_panti_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        SubjectId = new ArrayList<>();
        SubjectStatus = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();

     /*   bthapus = (ImageView)findViewById(R.id.hapus);
        bthapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteEmployee();
            }
        });*/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                // TODO Auto-generated method stub
                String str=SubjectId.get(index).toString();
                String status=SubjectStatus.get(index).toString();
                //Toast.makeText(TampilSemuaPanti.this, str, Toast.LENGTH_SHORT).show();
                valid(str, status);
                return true;
            }
        });
    }

    public void valid(final String str, final String status){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_choose, null);

        final ImageView ivValid, ivHapus, ivClose;

        ivValid= (ImageView)mView.findViewById(R.id.valid);
        ivHapus = (ImageView)mView.findViewById(R.id.hapus);
        ivClose = (ImageView)mView.findViewById(R.id.close);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        if(status.equalsIgnoreCase("1")){
            ivValid.setVisibility(View.GONE);
        }else{
            ivValid.setVisibility(View.VISIBLE);
        }
        ivValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(TampilSemuaPanti.this, "coba", Toast.LENGTH_SHORT).show();
                confirmValidasiEmployee(str);
            }
        });

        ivHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteEmployee(str);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void deleteEmployee(final String str){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaPantiJumlah.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilSemuaPantiJumlah.this, s, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),TampilSemuaPantiJumlah.class));
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_DELETE_EMP+str);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(final String str){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee(str);
                       // startActivity(new Intent(TampilSemuaPanti.this,TampilSemuaPanti.class));
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

    private void validasiEmployee(final String str){
        class ValidasiEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaPantiJumlah.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilSemuaPantiJumlah.this, s, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),TampilSemuaPantiJumlah.class));
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_VALIDASI_EMP+str);
                return s;
            }
        }

        ValidasiEmployee de = new ValidasiEmployee();
        de.execute();
    }

    private void confirmValidasiEmployee(final String str){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Konfirmasi data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        validasiEmployee(str);
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

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_panti = jo.getString(konfigurasi.TAG_ID);
                String nama_panti = jo.getString(konfigurasi.TAG_NAMA_PANTI);
                String alamat_panti = jo.getString(konfigurasi.TAG_ALAMAT_PANTI);
                SubjectId.add(jo.getString(konfigurasi.TAG_ID));
                SubjectStatus.add(jo.getString("status_valid"));
                String status_valid  = jo.getString("status_valid");
                String status_v = "";
                if(status_valid.equalsIgnoreCase("0")){
                    status_v = "Status : Belum Valid";
                }else if(status_valid.equalsIgnoreCase("1")){
                    status_v = "Status : Sudah Valid";
                }

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID,id_panti);
                employees.put(konfigurasi.TAG_NAMA_PANTI,nama_panti);
                employees.put(konfigurasi.TAG_ALAMAT_PANTI,alamat_panti);
                employees.put("status_valid",status_v);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaPantiJumlah.this, list, R.layout.list_item_data_panti_admin,
                new String[]{konfigurasi.TAG_ID, konfigurasi.TAG_NAMA_PANTI, konfigurasi.TAG_ALAMAT_PANTI,"status_valid"},
                new int[]{R.id.id_p, R.id.nama_p, R.id.alamat_p, R.id.status_valid});

        listView.setAdapter(adapter);

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaPantiJumlah.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       Intent intent = new Intent(this, MenuAdminJumlah.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(konfigurasi.TAG_ID).toString();
        SharedPreferences sharedPreferences = TampilSemuaPantiJumlah.this.getSharedPreferences(konfigurasi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(konfigurasi.LOGGEDIN_SHARED_PREF,true);
        editor.putString(konfigurasi.TAG_ID,empId);
        editor.commit();
        intent.putExtra("id_panti",empId);
        startActivity(intent);

       // Toast.makeText(this, empId, Toast.LENGTH_SHORT).show();
    }
}
