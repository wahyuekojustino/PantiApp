package com.android.pantiasuhan.pantiasuhan.Pengunjung;

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
import android.widget.Toast;

import com.android.pantiasuhan.pantiasuhan.R;


/**
 * Created by KHAN on 25/03/2018.
 */

public class MenuAdmin extends AppCompatActivity {
    CardView btAnak, btDonatur, btPengurus, btKebutuhan, btPanti, btkegiatan;
    Bundle extra;
    String id_panti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_baru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
        extra = getIntent().getExtras();

        id_panti=extra.getString(konfigurasi.TAG_ID);
       // Toast.makeText(this, id_panti, Toast.LENGTH_SHORT).show();

        btAnak=(CardView) findViewById(R.id.home_mAnak);
        btDonatur=(CardView) findViewById(R.id.home_mDonatur);
        btPengurus=(CardView) findViewById(R.id.home_mPengurus);
        btKebutuhan=(CardView) findViewById(R.id.home_mKebutuhan);
        btkegiatan=(CardView) findViewById(R.id.home_mKegiatan);
        btPanti=(CardView) findViewById(R.id.home_mPanti);


        btAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, TampilSemuaAnak.class));
            }
        });

        btDonatur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, TampilSemuaDonatur.class));
            }
        });

        btPengurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, TampilSemuaPengurus.class));
            }
        });

        btKebutuhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, TampilSemuaKebutuhan.class));
            }
        });

        btPanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MenuAdmin.this, id_panti, Toast.LENGTH_SHORT).show();
               startActivity(new Intent(MenuAdmin.this, DetailDataPanti.class));
            }
        });

        btkegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, TampilSemuaKegiatan.class));
            }
        });
    }

}
