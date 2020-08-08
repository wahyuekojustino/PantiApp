package com.android.pantiasuhan.pantiasuhan.donatur;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;

import java.util.HashMap;

public class Daftar_Donatur extends AppCompatActivity implements View.OnClickListener {
    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextNIK;
    private EditText editTextPassword;
    private EditText editTextNama_Donatur;
    private EditText editTextPekerjaan;
    private EditText editTextAlamat_Donatur;
    private EditText editTextTlp;

    private Button buttonAdd;

    private CoordinatorLayout coordinatorLayout;

    //SessionManager sessionManager;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar__donatur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   sessionManager = new SessionManager(getApplicationContext());
      //  HashMap<String, String> user  = sessionManager.getUserDetails();
      //  username = user.get(SessionManager.kunci_username);

        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));


        //Inisialisasi dari View
        editTextNIK =  (EditText) findViewById(R.id.etNIK);
        editTextNama_Donatur = (EditText) findViewById(R.id.etDonatur);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        editTextPekerjaan = (EditText) findViewById(R.id.etPekerjaan);
        editTextAlamat_Donatur = (EditText) findViewById(R.id.etAlamatDonatur);
        editTextTlp = (EditText) findViewById(R.id.etTlpDon);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLay);

        buttonAdd = (Button) findViewById(R.id.btnSimpan);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
    }
    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee(){
        final String NIK = editTextNIK.getText().toString().trim();
        final String Nama_Donatur = editTextNama_Donatur.getText().toString().trim();
        final String Password = editTextPassword.getText().toString().trim();
        final String Pekerjaan = editTextPekerjaan.getText().toString().trim();
        final String Alamat_Donatur = editTextAlamat_Donatur.getText().toString().trim();
        final String Tlp = editTextTlp.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Daftar_Donatur.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(s.equalsIgnoreCase("exist")){
                    editTextNIK.requestFocus();
                    editTextNIK.setError("NIK sudah terdaftar");
                    Toast.makeText(Daftar_Donatur.this,"NIK sudah terdaftar. Silahkan Login !", Toast.LENGTH_LONG).show();
                }else if(s.equalsIgnoreCase("sukses")){
                    Toast.makeText(Daftar_Donatur.this,"Selamat pendaftaran berhasil !", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Daftar_Donatur.this,"Gagal Mendaftar !", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                //params.put("username",username);

                params.put(konfigurasi.KEY_EMP_NIK,NIK);
                params.put(konfigurasi.KEY_EMP_NAMA_DONATUR,Nama_Donatur);
                params.put(konfigurasi.KEY_EMP_PASSWORD,Password);
                params.put(konfigurasi.KEY_EMP_PEKERJAAN, Pekerjaan);
                params.put(konfigurasi.KEY_EMP_ALAMAT_DONATUR, Alamat_Donatur);
                params.put(konfigurasi.KEY_EMP_TLP,Tlp);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

        editTextNIK.setText("");
        editTextNama_Donatur.setText("");
        editTextPassword.setText("");
        editTextPekerjaan.setText("");
        editTextAlamat_Donatur.setText("");
        editTextTlp.setText("");
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            if(editTextNIK.getText().toString().equalsIgnoreCase("")){
                editTextNIK.requestFocus();
                editTextNIK.setError("Silahkan isi form ini !");
            }else if(editTextNama_Donatur.getText().toString().equalsIgnoreCase("")){
                editTextNama_Donatur.requestFocus();
                editTextNama_Donatur.setError("Silahkan isi form ini !");
            }else if(editTextPassword.getText().toString().equalsIgnoreCase("")){
                editTextPassword.requestFocus();
                editTextPassword.setError("Silahkan isi form ini !");
            }else if(editTextPekerjaan.getText().toString().equalsIgnoreCase("")){
                editTextPekerjaan.requestFocus();
                editTextPekerjaan.setError("Silahkan isi form ini !");
            }else if(editTextAlamat_Donatur.getText().toString().equalsIgnoreCase("")){
                editTextAlamat_Donatur.requestFocus();
                editTextAlamat_Donatur.setError("Silahkan isi form ini !");
            }else if(editTextTlp.getText().toString().equalsIgnoreCase("")){
                editTextTlp.requestFocus();
                editTextTlp.setError("Silahkan isi form ini !");
            }else{
                if(editTextNIK.getText().toString().length() < 15){
                    editTextNIK.requestFocus();
                    editTextNIK.setError("Max 16, Min 15");
                }else{
                    Toast.makeText(Daftar_Donatur.this,"Simpan", Toast.LENGTH_LONG).show();
                    addEmployee();
                }

            }

        }

    }
}
