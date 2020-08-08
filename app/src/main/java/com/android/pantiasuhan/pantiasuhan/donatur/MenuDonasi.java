package com.android.pantiasuhan.pantiasuhan.donatur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.android.pantiasuhan.pantiasuhan.R;

import java.util.HashMap;


public class MenuDonasi extends AppCompatActivity {
    //Button btDonatur, btDonasi, btPanti, btkegiatan;
    CardView btDonatur, btTDonasi,btDonasi;
    Bundle extra;
    String nik;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_donasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user  = sessionManager.getUserDetails();
//        extra = getIntent().getExtras();
//
//        nik=extra.getString(konfigurasi.TAG_NIK);
        // Toast.makeText(this, id_panti, Toast.LENGTH_SHORT).show();

        btDonatur=(CardView)findViewById(R.id.home_mPanti);
       // btDonatur=(Button)findViewById(R.id.btDonatur);
        btTDonasi=(CardView) findViewById(R.id.home_mAnak);
        btDonasi=(CardView)findViewById(R.id.home_mPengurus);
       // btPanti=(Button)findViewById(R.id.btPanti);
        //btkegiatan=(Button)findViewById(R.id.btKegiatan);
        sessionManager = new SessionManager(getApplicationContext());

        btDonatur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDonasi.this, DetailDataDonatur.class));
            }
        });

        btTDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDonasi.this, DonasiActivity.class));
            }
        });

        btDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDonasi.this, TampilSemuaDonasi.class));
            }
        });

    }
}
